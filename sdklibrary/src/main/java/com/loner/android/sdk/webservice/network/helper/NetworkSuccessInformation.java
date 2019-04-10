package com.loner.android.sdk.webservice.network.helper;

import com.loner.android.sdk.utilities.Constant;

public class NetworkSuccessInformation {

    public NetworkSuccessInformation(int statusCode) {
        if(statusCode == Constant.STASK_Authentication)
           responseMsg = "User Successfully register";

    }

    public String getResponseMsg() {
        return responseMsg;
    }

    private String responseMsg;
}
