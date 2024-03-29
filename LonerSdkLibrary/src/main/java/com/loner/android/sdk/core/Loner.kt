package com.loner.android.sdk.core


import android.content.Context
import android.util.Log
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.interfaces.LocationUpdateInerface

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
        @JvmStatic
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
        @JvmStatic
        fun getClient(): Loner {
            synchronized(this) {
                if (instance == null) {
                    throw IllegalStateException("Please call Loner.initialize() before requesting the client.")
                }
                return instance as Loner

            }
        }

        /*  @JvmStatic
          val client: Loner
              @Synchronized get() {
                  if (instance == null) {
                      throw IllegalStateException("Please call Loner.initialize() before requesting the client.")
                  }
                  return instance as Loner
              }*/
    }

    /**
     * <p>Call Emergency alert API to send msg server</p>
     *
     * <p>This method call from App side, when app want to send emergency alert message to server,
     *  App can call this method when emergency slider swipe left to right or call from any event listener</p>
     *
     */
    abstract fun sendEmergencyAlertApi(context: Context, listener: ActivityCallBackInterface?)

    abstract fun sendAlertApi(context: Context, message: String, listener: ActivityCallBackInterface?)


    abstract fun getConfiguration(context: Context, listener: ActivityCallBackInterface?)

    abstract fun sendMessage(context: Context, message: String, listener: ActivityCallBackInterface?)

    abstract fun sendNotification(context: Context, message: String, listener: ActivityCallBackInterface?)

    abstract fun showCheckInAlertDialog(context: Context, title: String?, subject: String?, buttonText: String?)
    abstract fun getLocationUpdate(context: Context,listener: LocationUpdateInerface?)
    abstract fun sendLocation(context: Context)
    abstract fun checkPermission(context: Context,permissionResultCallback: PermissionResultCallback)
    abstract fun isPermissionGranted():Boolean
}
