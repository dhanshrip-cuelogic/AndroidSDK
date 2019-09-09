package com.loner.android.sdk.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner

class LocationUpdate(context: Context) {
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var mContext = context
    private var mLocation: Location? = null

    companion object {
        @Volatile
        private var locationUpdate: LocationUpdate? = null
        fun getInstance(context: Context): LocationUpdate {
            return locationUpdate ?: synchronized(this) {
                LocationUpdate(context).also {
                    locationUpdate = it
                }
            }
        }
    }

    fun getLastLocation() {

        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!(locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            Toast.makeText(mContext, mContext.resources.getString(R.string.gps_disable), Toast.LENGTH_LONG).show()
            return
        }
        val permission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        it?.let {
                            mLocation = it
                            Loner.getClient().sendLocation(mContext)
                        }

                    }
        } else {
            Toast.makeText(mContext, "Please check Location Perimission", Toast.LENGTH_LONG).show()
        }
    }

    fun getLocation(): Location? = mLocation



}