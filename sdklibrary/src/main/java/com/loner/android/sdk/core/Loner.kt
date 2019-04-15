package com.loner.android.sdk.core


import android.util.Log
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface

/**
 * <p>Loner sdk sample code  provides a Emergency slider widget.You can direct implement slider controller
 * into your activity. if you slide left to right then sdk call emergency alert.</p>
 */

abstract class Loner {


    companion object {
        private val TAG = Loner::class.java.simpleName
        @Volatile
        private var instance: Loner? = null

        /**
         * <p>Initializes the Loner singleton.</p>
         * <p>Must be called before trying to access the object with Loner.client</p>
         */
        @Synchronized
        fun initialize() {
            if (instance != null) {
                Log.e(TAG, "Loner has already been initialized")
                return
            }
            instance = RealLoner.create()
        }

        /**
         * <p>Provides access to the Loner client that is required for communication between sdk and app.</p>
         */
        val client: Loner
            @Synchronized get() {
                if (instance == null) {
                    throw IllegalStateException("Please call Loner.initialize() before requesting the client.")
                }
                return instance as Loner
            }
    }

    /**
     * <p>Call Emergency alert API to send msg server</p>
     *
     * <p>This method call from App side, when app want to send emergency alert message to server,
     *  App can call this method when emergency slider swipe left to right or call from any event listener</p>
     * @param ActivityCallBackInterface It's give a callback to app for Api request success or failure
     */
    abstract fun sendEmergencyAlertApi(listener: ActivityCallBackInterface)
}
