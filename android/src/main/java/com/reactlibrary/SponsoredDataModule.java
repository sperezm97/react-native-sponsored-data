package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class SponsoredDataModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

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
    public String dispatchRequest() {
        return "prueba";
    }

    @ReactMethod
    public void initializerAPI(String apiKey) {

    }

    @ReactMethod
    public void fetch() {
        
    }

    @ReactMethod
    public void post() {

    }

    @ReactMethod 
    public void put() {

    }

    @ReactMethod 
    public void delete() {

    }

}
