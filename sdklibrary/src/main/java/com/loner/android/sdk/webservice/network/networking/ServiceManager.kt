package com.loner.android.sdk.webservice.network.networking

import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.webservice.interfaces.APIsInterface
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.apis.AlertRequest
import com.loner.android.sdk.webservice.network.apis.BaseRequest
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import java.util.*

/**
 * <p>This files used as ServiceManager. This class is responsible to create instances
 * of different API classes and call their respective Request functions.
 * Note : This is a Singleton class.</p>
 */
class ServiceManager private constructor() : HTTPClientInterface, APIsInterface {
    private var alertRequest: AlertRequest? = null
    private val callbackMap: HashMap<BaseRequest, ActivityCallBackInterface> = HashMap()


    /**
     * <p>This method of  HTTPClientInterface
     * When the request api returns the success state 200 status code.</p>
     * @param responseObject this object provide call back object related API call classes.
     * @param baseDataObject it's provide a response data class class object
     * @param networkSuccessInformation it is provide success response message as per API call.
     */
    override fun onSuccess(responseObject: BaseRequest, baseDataObject: Any, networkSuccessInformation: NetworkSuccessInformation) {
        val listener = callbackMap[responseObject]
        if (listener != null) {
            listener.onResponseDataSuccess(networkSuccessInformation.responseMsg!!)
            callbackMap.remove(responseObject)
        }
    }
    /**<p>This method of  HTTPClientInterface
     * When the request api returns the failure.</p>
     * @param responseObject this object provide call back object related API call classes.
     * @param errorInformation this object provide error message related api.
     */
    override fun onFailure(responseObject: BaseRequest, errorInformation: NetworkErrorInformation) {
        val listener = callbackMap[responseObject]
        if (listener != null) {
            listener.onResponseDataFailure(errorInformation.detailMessage!!)
            callbackMap.remove(responseObject)
        }
    }

    /**
     * <p>This method of  APIsInterface </p>
     * This method create a object AlertRequest class.
     * @param responseDataListener this interface return a callback of API response
     */
    override fun sendAlertApi(responseDataListener: ActivityCallBackInterface) {
        alertRequest = AlertRequest(this)
        callbackMap[alertRequest as BaseRequest] = responseDataListener
        alertRequest!!.alert(alertRequest!!, Constant.STASK_AlertEmergency)

    }


    companion object {
        @Volatile
        private var serviceManagerInstance: ServiceManager? = null

        fun getInstance(): ServiceManager {
            return serviceManagerInstance ?: synchronized(this) {
                ServiceManager().also {
                    serviceManagerInstance = it
                }
            }
        }
    }
}