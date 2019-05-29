package com.loner.android.sdk.core

import android.content.Context
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
     *  @param ActivityCallBackInterface ActivityCallBackInterface It's give a callback to app for Api request success or failure
     */
    override fun sendEmergencyAlertApi(context:Context,listener: ActivityCallBackInterface) {
        serviceManager.sendAlertApi(context,listener)
    }

    override fun getConfiguration(context: Context, listener: ActivityCallBackInterface) {

        serviceManager.requestConfiguration(context,listener)
    }

    companion object {
        private lateinit var serviceManager:ServiceManager
        fun create(): RealLoner {
            serviceManager = ServiceManager.getInstance()
            return RealLoner()
        }
    }

    override fun sendMessage(context: Context, message: String, listener: ActivityCallBackInterface) {
        serviceManager.sendMessageApi(context, message, listener)
    }
}
