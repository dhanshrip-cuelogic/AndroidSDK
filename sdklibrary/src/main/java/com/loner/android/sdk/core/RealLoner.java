package com.loner.android.sdk.core;

import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface;
import com.loner.android.sdk.webservice.network.networking.ServiceManager;

class RealLoner extends Loner {

    private static ServiceManager serviceManager;

    static RealLoner create() {
        serviceManager = ServiceManager.getInstance();
        return new RealLoner();
    }

    @Override
    public void register(String value, ActivityCallBackInterface listener) {
        serviceManager.registerDevice(value, listener);
    }

    @Override
    public void function2(String value) {

    }
}
