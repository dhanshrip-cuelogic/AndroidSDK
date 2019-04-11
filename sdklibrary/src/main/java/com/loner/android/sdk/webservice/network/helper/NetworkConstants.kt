package com.loner.android.sdk.webservice.network.helper

/**
 * This class is used to store the network related constants.
 */
object NetworkConstants {

    val HEADER_AUTHORIZATION = "authorization"
    val WSSE_HEADER_STR = "X-WSSE"
    val SUCCESS_STR = "Success"
    val FAILURE_STR = "Failure"
    val RESPONSE_STR = "StoreResponse"
    val REQUEST_STR = "Request"
    val UNABLE_TO_PROCESS = "Unable to process request, please try again."
    val NETWORK_NOT_AVALIABLE = "Network not available."

    val FAILURE_PAYLOAD = "Failed while preparing a payload"
    val RESPONSE_PARSING_FAILURE = "Failed while parsing the response."

    /**
     * Enum for Request type.
     */
    enum class REQUEST_TYPE {
        POST, GET, PUT, DELETE
    }

    /**
     * Enum for StoreResponse status code.
     */
    enum class RESPONSE_STATUS_CODE {
        SUCCESS_200, FAILURE_401, FAILURE_403, FAILURE_404
    }
}
