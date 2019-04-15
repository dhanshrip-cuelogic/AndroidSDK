package com.loner.android.sdk.core

import android.app.Application
import android.util.Log

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
/**
 * <p>Loner sdk sample code  provides a Emergency slider widget.You can direct implement slider controller
 * into your activity. if you slide left to right then sdk call emergency alert.</p>
 */

abstract class Loner {


    companion object {
        private val TAG = Loner::class.java.simpleName
        @Volatile private var instance: Loner? = null

        /**
         * <p>Initializes the Intercom singleton.</p>
         *
         * <p>Must be called before trying to access the object with Intercom.client()</p>
         *
         * @param application Your app's Application object
         *
         */
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

    abstract fun registerDeviceApi(value: String, listener: ActivityCallBackInterface)
    abstract fun sendEmergencyAlertApi(listener: ActivityCallBackInterface)
}
