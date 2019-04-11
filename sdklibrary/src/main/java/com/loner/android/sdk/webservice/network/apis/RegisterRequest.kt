package com.loner.android.sdk.webservice.network.apis

import com.loner.android.sdk.model.request.ActivationCode
import com.loner.android.sdk.model.response.AccessToken
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRequest(accessToken: String, private val listener: HTTPClientInterface) : BaseRequest() {
    private var callRegistration: Call<AccessToken>? = null
    private val activationCode: ActivationCode = ActivationCode(accessToken)

    fun register(requestObject: BaseRequest, STASK_Authentication: Int) {
        callRegistration = interfaceAPI.register(activationCode)
        callRegistration!!.enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                if (response.code() == 200) {
                    val accessToken = response.body()
                    val networkSuccessInformation = NetworkSuccessInformation(STASK_Authentication)
                    listener.onSuccess(requestObject, accessToken!!, networkSuccessInformation)
                } else {
                    val errorInformation = NetworkErrorInformation()
                    errorInformation.error = "Error"
                    errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                    listener.onFailure(requestObject, errorInformation)
                }

            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                val errorInformation = NetworkErrorInformation()
                errorInformation.error = "Error"
                errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                listener.onFailure(requestObject, errorInformation)
                callRegistration!!.cancel()
            }
        })
    }

    companion object {
        private val TAG = RegisterRequest::class.java.simpleName
    }
}