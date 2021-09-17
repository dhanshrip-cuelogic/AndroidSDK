package com.loner.android.sdk.model.request

import com.google.gson.annotations.SerializedName


data class LocationRequest(

        @SerializedName("device_id")
        val device_id: String?,
        @SerializedName("latitude")
        val latitude: String?,
        @SerializedName("longitude")
        val longitude: String?,
        @SerializedName("altitude")
        val altitude: String?,
        @SerializedName("heading")
        val heading: String?,
        @SerializedName("speed")
        val speed: String?,
        @SerializedName("satellite_count")
        val satellite_count: String?,
        @SerializedName("date")
        val date: String?
)