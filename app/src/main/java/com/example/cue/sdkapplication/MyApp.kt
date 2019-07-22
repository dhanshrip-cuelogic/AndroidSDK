package com.example.cue.sdkapplication

import android.app.Application

import com.loner.android.sdk.core.Loner

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // we can do authentication here for sdk in feature
        Loner.initialize()

    }

}
