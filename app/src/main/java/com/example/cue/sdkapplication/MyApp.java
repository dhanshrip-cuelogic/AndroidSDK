package com.example.cue.sdkapplication;

import android.app.Application;

import com.loner.android.sdk.core.Loner;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Loner.initialize(this);
    }

}
