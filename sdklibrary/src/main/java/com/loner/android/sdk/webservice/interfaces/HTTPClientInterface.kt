package com.loner.android.sdk.webservice.interfaces

import com.loner.android.sdk.webservice.network.apis.BaseRequest
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import java.util.*

/**
 * This files used as HTTPClientInterface. where this class act as a interface/communicator between
 * the Sub api request and the Service manager class which listens the each ans every state of
 * the request. The Sub api request will implement this Interface.
 */

interface HTTPClientInterface {

    /**
     * When the request api returns the success state 200 status code.
     */
    fun onSuccess(responseObject: BaseRequest, baseDataObject:Any, networkSuccessInformation: NetworkSuccessInformation)

    /**
     * When the request api returns the failure.
     */
    fun onFailure(responseObject: BaseRequest, errorInformationObject: NetworkErrorInformation)

}
