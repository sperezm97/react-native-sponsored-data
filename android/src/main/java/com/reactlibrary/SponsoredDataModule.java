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
    public void setUpProxy(String apiKey, ReadableMap body, Promise promise){
        sponsoredData.setApiKey(apiKey);
        sponsoredData.setUniqid(body.getString("uniqid"));
        sponsoredData.setTransport(body.getString("transport"));
        try {
            client.getTokenWMC();
            promise.resolve();
        } catch(Exception e) {
            promise.reject(e.getMessage());
        }
    
    }

    @ReactMethod
    public void get(String endpoint, Promise promise) {
        try{
            String values = client.get(endpoint);
            promise.resolve(values);
        } catch (Exception error) {
            promise.reject(error.getMessage());
        }
    }

    @ReactMethod
    public void post(String endpoint, ReadableMap body, String token, Promise promise) {
        try {
            JSONObject result = new JSONObject(client.post(endpoint, body));
            promise.resolve(result);
            
        } catch (JSONException e) {
            promise.reject(e.getMessage());
        }
    }

    @ReactMethod
    public void put(String endpoint, ReadableMap body, String token, Promise promise) {
        try {
            String response = client.put(endpoint, body);
            JSONObject result = new JSONObject(response);
            promise.resolve(result);

        } catch (JSONException e) {
            promise.reject(e.getMessage());
        }
    }

    @ReactMethod
    public void delete(String endpoint, String token, Promise promise) {
        try {
            JSONObject result = new JSONObject(client.get(endpoint));
            promise.resolve(result);

        } catch (JSONException e) {
            promise.reject(e.getMessage());
        }
    }

}
