package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONException;
import org.json.JSONObject;

public class SponsoredDataModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private SponsoredData sponsoredData = SponsoredData.getInstance();
    private Client client = Client.getInstance();

    public SponsoredDataModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "SponsoredData";
    }

    @ReactMethod
    public void setUpProxy(String apiKey, ReadableMap body) throws JSONException {
        sponsoredData.setApiKey(apiKey);
        sponsoredData.setUniqid(body.getString("uniqid"));
        sponsoredData.setTransport(body.getString("transport"));
        client.getTokenWMC();
    }

    @ReactMethod
    public void get(String endpoint, Promise promise) {
        String values = client.get(endpoint);
        promise.resolve(values);
    }

    @ReactMethod
    public void post(String endpoint, ReadableMap body, Callback onResult) {
        try {
            JSONObject result = new JSONObject(client.post(endpoint, body));
            onResult.invoke(result);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void put(String endpoint, ReadableMap body, Callback onResult) {
        try {
            String response = client.put(endpoint, body);
            JSONObject result = new JSONObject(response);
            onResult.invoke(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void delete(String endpoint, Callback onResult) {
        try {
            JSONObject result = new JSONObject(client.get(endpoint));
            onResult.invoke(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
