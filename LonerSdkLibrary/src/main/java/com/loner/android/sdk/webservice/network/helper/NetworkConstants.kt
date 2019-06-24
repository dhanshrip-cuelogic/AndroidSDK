package com.loner.android.sdk.webservice.network.helper

/**
 * This class is used to store the network related constants.
 */
object NetworkConstants {

    const val HEADER_AUTHORIZATION = "authorization"
    const val WSSE_HEADER_STR = "X-WSSE"
    const val SUCCESS_STR = "Success"
    const val FAILURE_STR = "Failure"
    const val RESPONSE_STR = "StoreResponse"
    const val REQUEST_STR = "Request"
    const val UNABLE_TO_PROCESS = "Unable to process request, please try again."
    const val NETWORK_NOT_AVALIABLE = "Network not available."

    const val FAILURE_PAYLOAD = "Failed while preparing a payload"
    const val RESPONSE_PARSING_FAILURE = "Failed while parsing the response."

    /**
     * Enum for Request type.
     */
    enum class REQUESTTYPE {
        POST, GET, PUT, DELETE
    }

    /**
     * Enum for StoreResponse status code.
     */
    enum class RESPONSESTATUS_CODE {
        SUCCESS_200, FAILURE_401, FAILURE_403, FAILURE_404
    }
}
