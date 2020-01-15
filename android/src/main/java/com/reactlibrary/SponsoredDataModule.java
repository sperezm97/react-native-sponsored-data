package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

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
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }

    @ReactMethod
    public void setUpProxy(final String apiKey) {
        sponsoredData.setApiKey(apiKey);
        client.getTokenWMC();
    }

    @ReactMethod
    public void get(final String endpoint, final Promise promise) {
        try {
            JSONObject result = new JSONObject(client.get(endpoint));
            promise.resolve(result);

        } catch (JSONException e) {
            promise.reject(e.getMessage());
        }
    }

    @ReactMethod
    public void post(final String endpoint, final JSONObject body, final Callback onResult) {
        try {
            JSONObject result = new JSONObject(client.post(endpoint, body));
            onResult.invoke(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod 
    public void put(final String endpoint, final JSONObject body, final Callback onResult) {
        try {
            JSONObject result = new JSONObject(client.put(endpoint, body));
            onResult.invoke(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod 
    public void delete(final String endpoint, Call, final Callback onResult) {
        try {
            JSONObject result = new JSONObject(client.get(endpoint));
            onResult.invoke(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
