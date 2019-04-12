package com.loner.android.sdk.webservice.network.apis


import com.loner.android.sdk.model.request.RequestAlert
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.utils.Utility
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import com.loner.android.sdk.webservice.network.networking.NetworkStatus
import com.loner.android.sdk.webservice.network.networking.NetworkStatusReceiver

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlertRequest(private val listener: HTTPClientInterface) : BaseRequest() {
    private var alertCallObjet: Call<Void>? = null
    private val requestAlert: RequestAlert

    init {
        requestAlert = RequestAlert()
        requestAlert.alertType = "emergency_alert"
        requestAlert.data = Utility.currentDate
        requestAlert.deviceId = Constant.deviceId
    }

    fun alert(requestObject: BaseRequest, STASK_AlertEmergency: Int) {
            alertCallObjet = interfaceAPI.alert(requestAlert)
            alertCallObjet!!.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        val networkSuccessInformation = NetworkSuccessInformation(STASK_AlertEmergency)
                        listener.onSuccess(requestObject, response, networkSuccessInformation)
                    } else {
                        val errorInformation = NetworkErrorInformation()
                        errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                        listener.onFailure(requestObject, errorInformation)
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    val errorInformation = NetworkErrorInformation()
                    errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                    listener.onFailure(requestObject, errorInformation)
                    alertCallObjet!!.cancel()

                }
            })
        }

}
