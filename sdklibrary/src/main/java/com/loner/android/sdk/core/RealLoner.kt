package com.loner.android.sdk.core

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
    override fun sendEmergencyAlertApi(listener: ActivityCallBackInterface) {
        serviceManager!!.sendAlertApi(listener)
    }

    companion object {
        private var serviceManager: ServiceManager? = null
        fun create(): RealLoner {
            serviceManager = ServiceManager.getInstance()
            return RealLoner()
        }
    }


}
