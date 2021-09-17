package com.loner.android.sdk.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log


class GPSSettingReceiver : BroadcastReceiver() {
    private var gpsSettingListener: GPSSettingListener? = null
    override fun onReceive(context: Context, intent: Intent) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            gpsSettingListener?.onGPSEnabled(context)
        }else{
            gpsSettingListener?.onGPSDisabled(context)
        }
    }

    interface GPSSettingListener{
        fun onGPSEnabled(context: Context)
        fun onGPSDisabled(context: Context)
    }

    fun setGPSSettingListener(gpsSettingListener: GPSSettingListener){
        this.gpsSettingListener = gpsSettingListener
    }

    fun removeGPSSettingListener(){
        gpsSettingListener = null
    }

}
