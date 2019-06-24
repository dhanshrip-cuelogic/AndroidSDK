package com.loner.android.sdk.core


import android.content.Context
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
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
    override fun sendEmergencyAlertApi(context:Context,listener: ActivityCallBackInterface) {
        serviceManager.sendAlertApi(context,"emergency_alert",listener)
    }

    override fun sendAlertApi(context: Context, message: String, listener: ActivityCallBackInterface) {
        serviceManager.sendAlertApi(context,message,listener)
    }

    override fun getConfiguration(context: Context, listener: ActivityCallBackInterface) {

        serviceManager.requestConfiguration(context,listener)
    }

    companion object {
        private lateinit var serviceManager:ServiceManager
        private lateinit var lonerDialog: LonerDialog
        fun create(): RealLoner {
            serviceManager = ServiceManager.getInstance()
            lonerDialog = LonerDialog.getInstance()
            return RealLoner()
        }
    }

    override fun sendNotification(context: Context, message: String, listener: ActivityCallBackInterface) {
        serviceManager.sendNotificationApi(context, message, listener)
    }

    override fun sendMessage(context: Context, message: String, listener: ActivityCallBackInterface) {
        serviceManager.sendMessageApi(context, message, listener)
    }


    override fun showCheckInAlertDialog(context: Context, title: String?, subject: String?, buttonText: String?) {
        lonerDialog.showCheckInAlert(context,title,
               subject,null)
    }
}
