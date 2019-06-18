package com.loner.android.sdk.webservice.interfaces


/**
 * This class will act as a interface to listen the response from the server
 * between the service manager class, from the service layer and any of the view controller
 * from the view and the requesting activity will always implements this interface.
 */
interface ActivityCallBackInterface {

    /**
     * Will trigger when the request is successful.
     */
    fun onResponseDataSuccess(successResponse: String)

    /**
     * Will trigger when the request has failed.
     */
    fun onResponseDataFailure(failureResponse: String)
}
