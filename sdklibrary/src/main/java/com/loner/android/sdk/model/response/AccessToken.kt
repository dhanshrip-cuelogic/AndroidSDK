package com.loner.android.sdk.model.response

import com.google.gson.annotations.SerializedName
import com.loner.android.sdk.webservice.models.BaseData

class AccessToken : BaseData() {

    @SerializedName("device_id")
    var deviceId: String? = null
    @SerializedName("access_token")
    var accessToken: String? = null


    override fun toString(): String {
        return "AccessToken{" +
                "deviceId='" + deviceId + '\''.toString() +
                ", accessToken='" + accessToken + '\''.toString() +
                '}'.toString()
    }
}
