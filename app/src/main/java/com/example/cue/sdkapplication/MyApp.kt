package com.example.cue.sdkapplication

import android.app.Application

import com.loner.android.sdk.core.Loner

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Loner.initialize(this)
    }

}
