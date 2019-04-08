package com.loner.android.sdk.webservice.network.helper;

/**
 * This class is used to store the network related constants.
 */
public class NetworkConstants {

    public static final String HEADER_AUTHORIZATION = "authorization";
    public static final String WSSE_HEADER_STR = "X-WSSE";
    public static final String SUCCESS_STR = "Success";
    public static final String FAILURE_STR = "Failure";
    public static final String RESPONSE_STR = "StoreResponse";
    public static final String REQUEST_STR = "Request";
    public static final String UNABLE_TO_PROCESS = "Unable to process request, please try again.";
    public static final String NETWORK_NOT_AVALIABLE = "Network not available.";

    public static final String FAILURE_PAYLOAD = "Failed while preparing a payload";
    public static final String RESPONSE_PARSING_FAILURE = "Failed while parsing the response.";

    /**
     * Enum for Request type.
     */
    public enum REQUEST_TYPE {
        POST, GET, PUT, DELETE;
    }

    /**
     * Enum for StoreResponse status code.
     */
    public enum RESPONSE_STATUS_CODE {
        SUCCESS_200, FAILURE_401, FAILURE_403, FAILURE_404;
    }
}
