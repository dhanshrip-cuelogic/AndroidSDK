package com.loner.android.sdk.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager



class GPSSettingReceiver : BroadcastReceiver() {
    private var gpsSettingListener: GPSSettingListener? = null
    override fun onReceive(context: Context, intent: Intent) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsSettingListener?.onGPSEnabled()
        }else{
            gpsSettingListener?.onGPSDisabled()
        }
    }

    interface GPSSettingListener{
        fun onGPSEnabled()
        fun onGPSDisabled()
    }

    fun setGPSSettingListener(gpsSettingListener: GPSSettingListener){
        this.gpsSettingListener = gpsSettingListener
    }

    fun removeGPSSettingListener(){
        gpsSettingListener = null
    }

}
