package com.loner.android.sdk.webservice.network.networking

import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.webservice.interfaces.APIsInterface
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.apis.AlertRequest
import com.loner.android.sdk.webservice.network.apis.BaseRequest
import com.loner.android.sdk.webservice.network.apis.RegisterRequest
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import java.util.*

/**
 * This files used as ServiceManager. This class is responsible to create instances
 * of different API classes and call their respective Request functions.
 * Note : This is a Singleton class.
 */
class ServiceManager private constructor() : HTTPClientInterface, APIsInterface {
    private var registerRequest: RegisterRequest? = null
    private var alertRequest: AlertRequest? = null
    private val callbackMap: HashMap<BaseRequest, ActivityCallBackInterface> = HashMap()

    override fun onSuccess(responseObject: BaseRequest, baseDataObject: Any, networkSuccessInformation: NetworkSuccessInformation) {
        val listener = callbackMap[responseObject]
        if (listener != null) {
            listener.onResponseDataSuccess(networkSuccessInformation.responseMsg!!)
            callbackMap.remove(responseObject)
        }
    }

    override fun onFailure(responseObject: BaseRequest, errorInformation: NetworkErrorInformation) {
        val listener = callbackMap[responseObject]
        if (listener != null) {
            listener.onResponseDataFailure(errorInformation.detailMessage!!)
            callbackMap.remove(responseObject)
        }
    }

    override fun registerDevice(accessToken: String, responseDataListener: ActivityCallBackInterface) =
            if (!accessToken.isEmpty()) {
                registerRequest = RegisterRequest(accessToken, this)
                callbackMap[registerRequest as BaseRequest] = responseDataListener
                registerRequest!!.register(registerRequest!!, Constant.STASK_Authentication)
            } else {
                responseDataListener.onResponseDataFailure("AccessToken can not empty")
            }


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