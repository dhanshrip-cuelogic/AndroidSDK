package com.example.cue.sdkapplication


import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.widget.CheckInTimerView
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.model.respons.LonerPermission

class MainActivity : FragmentActivity(), OnMapReadyCallback, PermissionResultCallback {
    private lateinit var checkInTimerView: CheckInTimerView
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInTimerView = findViewById(R.id.check_view)
        checkInTimerView.loadCheckInTimerComponent(this@MainActivity, true, true, 10)
    }

    override fun onPermissionGranted() {
        Loner.getClient().sendLocationUpdate(this@MainActivity)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@MainActivity)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mapSettings = mMap.uiSettings
        mapSettings.isZoomControlsEnabled = true
        mapSettings.isZoomGesturesEnabled = true
        mapSettings.isScrollGesturesEnabled = true
        mapSettings.isTiltGesturesEnabled = true
        if (mMap != null) {
            mMap.isMyLocationEnabled = true
        }

    }
}
