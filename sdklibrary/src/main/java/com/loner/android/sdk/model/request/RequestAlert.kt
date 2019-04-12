package com.loner.android.sdk.model.request

import com.google.gson.annotations.SerializedName

class RequestAlert {

    @SerializedName("device_id")
    var deviceId: String? = null

    @SerializedName("type")
    var alertType: String? = null

    @SerializedName("date")
    var data: String? = null


}
