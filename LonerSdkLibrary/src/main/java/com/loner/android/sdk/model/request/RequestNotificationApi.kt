package com.loner.android.sdk.model.request

import com.google.gson.annotations.SerializedName

class RequestNotificationApi {

    @SerializedName("device_id")
    var deviceId: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("date")
    var data: String? = null
}