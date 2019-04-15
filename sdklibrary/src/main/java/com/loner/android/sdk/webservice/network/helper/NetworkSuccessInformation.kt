package com.loner.android.sdk.webservice.network.helper

import com.loner.android.sdk.utils.Constant

/**
 * This class filter a response message according to API request.
 */
class NetworkSuccessInformation(statusCode: Int) {

    var responseMsg: String? = null
        private set

    init {
        if (statusCode == Constant.STASK_Authentication)
            responseMsg = "User Successfully register"
        if( statusCode == Constant.STASK_AlertEmergency)
            responseMsg = "Emergency alert successfully triggered"
    }
}
