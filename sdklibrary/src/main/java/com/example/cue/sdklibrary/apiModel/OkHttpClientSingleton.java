package com.example.cue.sdklibrary.apiModel;

import android.util.Log;

import okhttp3.OkHttpClient;

public class OkHttpClientSingleton extends OkHttpClient {
    private static OkHttpClientSingleton INSTANCE ;
    private OkHttpClientSingleton() { }
    public static OkHttpClientSingleton getInstance()  {
        Log.d("OkHttpClient", "Method call for instance");
        if (INSTANCE == null) {
            Log.d("OkHttpClient", "Instance created");
            INSTANCE = new OkHttpClientSingleton();
            }
        return INSTANCE;
    }

}
