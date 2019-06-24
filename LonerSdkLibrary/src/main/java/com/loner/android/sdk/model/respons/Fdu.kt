package com.loner.android.sdk.model.respons

import com.google.gson.annotations.SerializedName

class Fdu {
    @SerializedName("fall_detection_enabled")
    var isFallDetectionEnabled:Boolean? = null

    @SerializedName("no_motion_enabled")
    var isNoMotionEnabled:Boolean? = null

    @SerializedName("no_motion_timeout")
    var noMotionTimeOut:Int = 0

    @SerializedName("manual_checkin_enabled")
    var isManualCheckInEnabled:Boolean? = null

    @SerializedName("manual_checkin_timeout")
    var manualCheckInTimeOut:Int = 0

    @SerializedName("motion_sensitivity")
    var motionSensiivity:Int = 0

    @SerializedName("silent_alarm_enabled")
    var isSilentAlarmEnabled:Boolean? = null

    @SerializedName("emergency_alarm_enabled")
    var isEmergencyAlarmEnabled:Boolean? = null

    @SerializedName("visual_alarm_enabled")
    var isVisualAlarmEnabled:Boolean? = null

    @SerializedName("use_gps_as_motion")
    var isGpsMotionaEnabled:Boolean? = null

    @SerializedName("gps_motion_cutoff_speed")
    var gpsMotionCutoffSpeed:Int = 0

    @SerializedName("alarm_acknowledge_timeout")
    var alarmAcknowledgeTimeOut:Int = 0

    @SerializedName("gsm_lost_alarm_timeout")
    var gsmLostAlarmTimeOut:Int = 0

    @SerializedName("low_battery_alarm_level")
    var lowBatteryAlarmLevel:Int = 0

    @SerializedName("gsm_lost_alarm_enabled")
    var isGsmLostAlarmEnabled:Boolean? = null
}