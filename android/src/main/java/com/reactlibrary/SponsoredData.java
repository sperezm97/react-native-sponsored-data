package com.reactlibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class SponsoredData {

    private String proxyUsername;
    private String proxyPassword;
    private String sessionId;
    private String expire_seconds;
    private String apiKey;
    private String apiToken;
    private String proxyBaseURL;
    private int proxyPort;
    private static SponsoredData single_instance = null;

    public SponsoredData() {

    }

    public static SponsoredData getInstance()
    {
        if (single_instance == null) {
            single_instance = new SponsoredData();
        }

        return single_instance;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(String expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getProxyBaseURL() {
        return proxyBaseURL;
    }

    public void setProxyBaseURL(String proxyBaseURL) {
        this.proxyBaseURL = proxyBaseURL;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public JSONObject createUserBody() throws JSONException {
        return new JSONObject()
                .put("Token", getApiToken() )
                .put("mcc","370")
                .put("mnc", "02")
                .put("transport", "wifi")
                .put("uniqid", "112345");
    }

    public void setUser(String user) {
        try {
            JSONObject jsonObject = new JSONObject(user);
            setProxyBaseURL(jsonObject.getString("gw").substring(0,39));
            setProxyPort(Integer.parseInt(jsonObject.getString("gw").substring(40)));
            setProxyUsername(jsonObject.getString("username"));
            setProxyPassword(jsonObject.getString("password"));
            setSessionId(jsonObject.getString("sessionid"));
            setExpire_seconds(jsonObject.getString("expire_seconds"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
