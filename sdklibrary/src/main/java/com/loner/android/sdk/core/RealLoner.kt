package com.loner.android.sdk.core

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.network.networking.ServiceManager

internal class RealLoner : Loner() {

    override fun registerDeviceApi(value: String, listener: ActivityCallBackInterface) {
        serviceManager!!.registerDevice(value, listener)
    }

    companion object {
        private var serviceManager: ServiceManager? = null

        fun create(): RealLoner {
            serviceManager = ServiceManager.instance
            return RealLoner()
        }
    }


}
