package com.loner.android.sdk.model.request

import com.google.gson.annotations.SerializedName

class RequestMessageApi {

    @SerializedName("device_id")
    var deviceId: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("date")
    var data: String? = null

}