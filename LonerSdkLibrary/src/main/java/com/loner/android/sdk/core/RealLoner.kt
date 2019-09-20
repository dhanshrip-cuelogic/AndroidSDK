package com.loner.android.sdk.core


import android.content.Context
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.model.respons.LonerPermission
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.interfaces.LocationUpdateInerface
import com.loner.android.sdk.webservice.network.networking.ServiceManager

/**
 * <p> This class implement method of Loner abstract class, This class is singleton</p>
 * <p> It will also create ServiceManger object when it will create own object. ServiceManger class is
 *    responsible for all API call.</p>
 */
 class RealLoner private constructor() : Loner() {

    /**
     * <p>This is implementation of abstract method of Loner class,
     *    Where sendAlertApi method of ServiceManger call and it send ActivityCallBackInterface interface
     *    for callback</p>
     *
     */
    private var locationListener:LocationUpdateInerface? = null
    override fun sendEmergencyAlertApi(context:Context,listener: ActivityCallBackInterface?) {
        serviceManager.sendAlertApi(context,"emergency_alert",listener)
    }

    override fun sendAlertApi(context: Context, message: String, listener: ActivityCallBackInterface?) {
        serviceManager.sendAlertApi(context,message,listener)
    }

    override fun getConfiguration(context: Context, listener: ActivityCallBackInterface?) {

        serviceManager.requestConfiguration(context,listener)
    }

    companion object {
        private lateinit var serviceManager:ServiceManager
        private lateinit var lonerDialog: LonerDialog
        private var isPermissionGranted: Boolean = false
        fun create(): RealLoner {
            serviceManager = ServiceManager.getInstance()
            lonerDialog = LonerDialog.getInstance()
            return RealLoner()
        }
    }

    override fun sendNotification(context: Context, message: String, listener: ActivityCallBackInterface?) {
        serviceManager.sendNotificationApi(context, message, listener)
    }

    override fun sendMessage(context: Context, message: String, listener: ActivityCallBackInterface?) {
        serviceManager.sendMessageApi(context, message, listener)
    }


    override fun showCheckInAlertDialog(context: Context, title: String?, subject: String?, buttonText: String?) {
        lonerDialog.showCheckInAlert(context,title,
               subject,null)
    }

    override fun getLocationUpdate(context: Context,listener: LocationUpdateInerface?) {
      LocationUpdate.getInstance(context).getLastLocation()
        listener?.let {
            locationListener = listener
        }
    }

    override fun sendLocation(context: Context) {
       var mLocation =  LocationUpdate.getInstance(context).getLocation()
       mLocation?.let {
         serviceManager.sendLocationApi(context,mLocation!!,null)
         locationListener?.onLocationChange(mLocation)
       }
    }

    override fun checkPermission(context: Context,permissionResultCallback: PermissionResultCallback){
        LonerPermission.getInstance(context, object : PermissionResultCallback {
            override fun onPermissionGranted() {
                isPermissionGranted = true
                permissionResultCallback.onPermissionGranted()
            }
        }).checkPermissions()
    }

    override fun isPermissionGranted(): Boolean {
       return isPermissionGranted
    }


}
