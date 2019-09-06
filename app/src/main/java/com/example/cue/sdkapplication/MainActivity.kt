package com.example.cue.sdkapplication


import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.model.respons.LonerPermission
import com.loner.android.sdk.widget.CheckInTimerView


class MainActivity : FragmentActivity(), OnMapReadyCallback{
    private lateinit var checkInTimerView: CheckInTimerView
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInTimerView = findViewById(R.id.check_view)
        checkInTimerView.loadCheckInTimerComponent(this,true,true, 10)


        LonerPermission.getInstance(this,object :PermissionResultCallback{
            override fun onPermissionGranted() {
                val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this@MainActivity)
            }
        }).checkPermissions()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mapSettings = mMap.uiSettings
        mapSettings.isZoomControlsEnabled = true
        mapSettings.isZoomGesturesEnabled = true
        mapSettings.isScrollGesturesEnabled = true
        mapSettings.isTiltGesturesEnabled = true

    }
}
