package com.loner.android.sdk.core

import android.app.Application
import android.util.Log

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface

abstract class Loner {

    abstract fun registerDeviceApi(value: String, listener: ActivityCallBackInterface)

    companion object {

        private val TAG = Loner::class.java.simpleName
        private var instance: Loner? = null

        @Synchronized
        fun initialize(application: Application) {
            if (instance != null) {
                Log.e(TAG, "Loner has already been initialized")
                return
            }
            instance = RealLoner.create()
        }

        val client: Loner
            @Synchronized get() {
                if (instance == null) {
                    throw IllegalStateException("Please call Loner.initialize() before requesting the client.")
                }
                return instance as Loner
            }
    }


}
