package com.loner.android.sdk.webservice.network.apis


import com.loner.android.sdk.model.request.RequestNotificationApi
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.utils.Utility
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRequest(message: String, private val listener: HTTPClientInterface):BaseRequest()  {
    private var NotificationRetrofitObject: Call<Void>? = null
    private val requestNotificationApi = RequestNotificationApi()

    init {
        requestNotificationApi.type = message
        requestNotificationApi.deviceId = Constant.deviceId
        requestNotificationApi.data = Utility.currentDate
    }

    fun notificationToServer(requestObject: BaseRequest, STASK_NOTIFICATION: Int) {
        NotificationRetrofitObject = interfaceAPI.notification(requestNotificationApi)
        NotificationRetrofitObject?.enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                val errorInformation = NetworkErrorInformation()
                errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                listener.onFailure(requestObject, errorInformation)
                NotificationRetrofitObject!!.cancel()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    val networkSuccessInformation = NetworkSuccessInformation(STASK_NOTIFICATION)
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