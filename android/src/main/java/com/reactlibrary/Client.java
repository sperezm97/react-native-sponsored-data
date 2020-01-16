package com.reactlibrary;

import android.os.StrictMode;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

public class Client {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    final SponsoredData sponsored = SponsoredData.getInstance();
    private MapUtil convert = new MapUtil();
    InetSocketAddress proxyAddr;
    Proxy proxy;
    private static Client single_instance = null;

    public Client() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    public static Client getInstance(){
        if (single_instance == null) {
            single_instance = new Client();
        }

        return single_instance;
    }

    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();


        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestingHost().equalsIgnoreCase(sponsored.getProxyBaseURL())) {
                    if (sponsored.getProxyPort() == getRequestingPort()) {
                        return new PasswordAuthentication(sponsored.getProxyUsername(), sponsored.getProxyPassword().toCharArray());
                    }
                }
                return null;
            }
        });
        client.newBuilder().proxy(proxy).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String put(String url, ReadableMap body) {
        Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create(JSON, convert.toJSONObject(body).toString()))
                .build();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestingHost().equalsIgnoreCase(sponsored.getProxyBaseURL())) {
                    if (sponsored.getProxyPort() == getRequestingPort()) {
                        return new PasswordAuthentication(sponsored.getProxyUsername(), sponsored.getProxyPassword().toCharArray());
                    }
                }
                return null;
            }
        });
        client.newBuilder().proxy(proxy).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String post(String url, ReadableMap body) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, convert.toJSONObject(body).toString()))
                .build();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestingHost().equalsIgnoreCase(sponsored.getProxyBaseURL())) {
                    if (sponsored.getProxyPort() == getRequestingPort()) {
                        return new PasswordAuthentication(sponsored.getProxyUsername(), sponsored.getProxyPassword().toCharArray());
                    }
                }
                return null;
            }
        });
        client.newBuilder().proxy(proxy).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String url) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestingHost().equalsIgnoreCase(sponsored.getProxyBaseURL())) {
                    if (sponsored.getProxyPort() == getRequestingPort()) {
                        return new PasswordAuthentication(sponsored.getProxyUsername(), sponsored.getProxyPassword().toCharArray());
                    }
                }
                return null;
            }
        });
        client.newBuilder().proxy(proxy).build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getTokenWMC() {
        JSONObject body = null;
        try {
            body = new JSONObject()
                    .put("apiKey", sponsored.getApiKey());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Request request = new Request.Builder()
                .url("https://api.worldmobileconnection.com/v2/gettoken")
                .post(RequestBody.create(JSON, body.toString()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                sponsored.setApiToken(new JSONObject(result).getString("Token"));
                getUserInfoWMC();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfoWMC() {
        JSONObject body = null;
        try {
            body = sponsored.createUserBody();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url("https://api.worldmobileconnection.com/v2/getuser")
                .post(RequestBody.create(JSON, body.toString()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                sponsored.setUser(result);
                setProxyWMC();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProxyWMC() {
        proxyAddr = new InetSocketAddress(sponsored.getProxyBaseURL(), sponsored.getProxyPort());
        proxy = new Proxy(Proxy.Type.HTTP, proxyAddr.createUnresolved(sponsored.getProxyBaseURL(), sponsored.getProxyPort()));
    }
}
