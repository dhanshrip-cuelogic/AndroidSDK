package com.loner.android.sdk.core;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface;


public abstract class Loner {

    private static final String TAG = Loner.class.getSimpleName();

    private static @Nullable
    Loner instance;

    public static synchronized void initialize(Application application) {
        if (instance != null) {
            Log.e(TAG, "Loner has already been initialized");
            return;
        }
        instance = RealLoner.create();
    }

    public static synchronized Loner getClient() {
        if (instance == null) {
            throw new IllegalStateException("Please call Loner.initialize() before requesting the client.");
        }
        return instance;
    }

    public abstract void registerDeviceApi(String value, ActivityCallBackInterface listener);


}
