package com.loner.android.sdk.webservice.network.helper

import com.loner.android.sdk.utils.Constant

/**
 * This class filter a response message according to API request.
 */
class NetworkSuccessInformation(statusCode: Int) {

    var responseMsg: String? = null
        private set

    init {
        when (statusCode) {
            Constant.STASK_Authentication -> responseMsg = "User Successfully register"
            Constant.STASK_AlertEmergency -> responseMsg = "Emergency alert successfully triggered"
            Constant.STASK_Configuration -> responseMsg = "Configuration fetch successfully"
            Constant.STASK_MESSAGE -> responseMsg = "Send Message to server successfully"
        }

    }
}
