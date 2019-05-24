package com.loner.android.sdk.model.respons

import com.google.gson.annotations.SerializedName

class Mobile {

    @SerializedName("language")
    var language:String? = null

    @SerializedName("allow_user_to_configure")
    var isAllowUserToConfigure:Boolean? = null

    @SerializedName("fdu_required")
    var isFduRequired:Boolean? = null

    @SerializedName("gps_publish_period")
    var gps_publish_period:Int = 0

    @SerializedName("automatic_shutdown_threshold")
    var automaticShutdownThreshold:Int = 0

    @SerializedName("alarm_sound_enabled")
    var isAlarmSoundEnabled:Boolean? = null

    @SerializedName("alarm_vibration_enabled")
    var isAlarmVibrationEnabled:Boolean? = null

    @SerializedName("data_logging_enabled")
    var isDataLoggingEnabled:Boolean? = null

    @SerializedName("developer_mode_enabled")
    var isDeveloperModeEnabled:Boolean? = null
}