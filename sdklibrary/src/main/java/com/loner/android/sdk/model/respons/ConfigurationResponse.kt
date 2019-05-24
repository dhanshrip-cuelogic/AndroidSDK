package com.loner.android.sdk.model.respons

import com.google.gson.annotations.SerializedName

class ConfigurationResponse {

    @SerializedName("date_created")
    var dateCreated: String? = null

    @SerializedName("network_timeout")
    var networkTimeOut: String? = null

    @SerializedName("fdu")
    var fdu : Fdu? = null

    @SerializedName("mobile")
    var mobile : Mobile? = null

}