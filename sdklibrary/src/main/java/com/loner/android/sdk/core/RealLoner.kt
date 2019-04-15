package com.loner.android.sdk.core

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.network.networking.ServiceManager

 class RealLoner private constructor() : Loner() {

    override fun registerDeviceApi(value: String, listener: ActivityCallBackInterface) {
        serviceManager!!.registerDevice(value, listener)
    }

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
