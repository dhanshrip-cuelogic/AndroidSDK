package com.loner.android.sdk.webservice.network.apis

import android.location.Location
import com.loner.android.sdk.model.request.LocationRequest
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.utils.Utility
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationUpdate(mLocation: Location?, private val listener: HTTPClientInterface) : BaseRequest() {
    private var locationRequestObject: Call<Void>? = null
    private val requestLocation = LocationRequest(Constant.deviceId, mLocation?.latitude.toString(),
            mLocation?.longitude.toString(), mLocation?.altitude.toString(), "0", mLocation?.speed.toString(), "0",
            Utility.currentDate)

    fun sendLocation(requestObject: BaseRequest, STASK_LOCATION: Int) {
        locationRequestObject = interfaceAPI.sendLocation(requestLocation)
        locationRequestObject?.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                val errorInformation = NetworkErrorInformation()
                errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                listener.onFailure(requestObject, errorInformation)
                locationRequestObject?.cancel()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    val networkSuccessInformation = NetworkSuccessInformation(STASK_LOCATION)
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