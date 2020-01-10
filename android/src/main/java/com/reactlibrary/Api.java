package com.reactlibrary;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

public class Api  {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    private String url = "https://jsonplaceholder.typicode.com/posts";


    private String value;
    private String baseUrl = "https://api.worldmobileconnection.com/v2";
    private String gw;
    private int port;
    private String username;
    private String password;
    private String sessionid;
    private String expire_seconds;
    private String apiKey;
    private String apiToken;

    public Api(String key) {
        apiKey = key;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private JSONObject createBody() {
        JSONObject body = new JSONObject();
        try {
            body.put("token", apiToken)
                    .put("mcc", "370")
                    .put("mnc", "02")
                    .put("uniqid", "123456")
                    .put("transport", "mobile");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

    private void fetchUser() {
        String endpoint = "/getuser";

        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(RequestBody.create(JSON, createBody().toString() ))
                .build();


        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                setUser(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUser(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            gw = jsonObject.getString("gw").substring(0,39);
            port = Integer.parseInt(jsonObject.getString("gw").substring(40));
            username = jsonObject.getString("username");
            password = jsonObject.getString("password");
            sessionid = jsonObject.getString("sessionid");
            expire_seconds = jsonObject.getString("expire_seconds");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void run(){

        Request request = new Request.Builder()
                .url(url)
                .header("sessionid", sessionid)
                .build();

        InetSocketAddress proxyAddr = new InetSocketAddress(gw, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr.createUnresolved(gw, port));

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestingHost().equalsIgnoreCase(gw)) {
                    if (port == getRequestingPort()) {
                        return new PasswordAuthentication(username, password.toCharArray());
                    }
                }
                return null;
            }
        });


        client.newBuilder().proxy(proxy).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() ) {
                setValue(response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getToken() {
        String endpoint = "/gettoken";
        JSONObject body = new JSONObject();

        try {
            body.put("apikey", apiKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(RequestBody.create(JSON, body.toString()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                saveToken(response.body().string());
                fetchUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveToken(String value) {
        try {
            JSONObject jsonToken = new JSONObject(value);
            apiToken = jsonToken.getString("Token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

