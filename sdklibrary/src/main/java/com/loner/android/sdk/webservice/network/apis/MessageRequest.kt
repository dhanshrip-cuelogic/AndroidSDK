package com.loner.android.sdk.webservice.network.apis

import com.loner.android.sdk.model.request.RequestMessageApi
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.utils.Utility
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRequest(private val message: String, private val listener: HTTPClientInterface):BaseRequest() {
    private var messageRetrofitObject: Call<Void>? = null
    private val requestMessageModule: RequestMessageApi = RequestMessageApi()

    init {
        requestMessageModule.message = message
        requestMessageModule.deviceId = Constant.deviceId
        requestMessageModule.data = Utility.currentDate
    }

    fun messageToServer(requestObject: BaseRequest, STASK_MESSAGE: Int) {
        messageRetrofitObject = interfaceAPI.message(requestMessageModule)
        messageRetrofitObject?.enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                val errorInformation = NetworkErrorInformation()
                errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                listener.onFailure(requestObject, errorInformation)
                messageRetrofitObject!!.cancel()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    val networkSuccessInformation = NetworkSuccessInformation(STASK_MESSAGE)
                    listener.onSuccess(requestObject, response, networkSuccessInformation)
                } else {
                    val errorInformation = NetworkErrorInformation()
                    errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                    listener.onFailure(requestObject, errorInformation)
                }
            }
        })

    }

}