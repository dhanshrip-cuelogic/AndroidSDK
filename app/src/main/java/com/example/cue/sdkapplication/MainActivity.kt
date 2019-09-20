package com.example.cue.sdkapplication


import android.location.Location
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.loner.android.sdk.widget.CheckInTimerView
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.LocationUpdateInerface
import com.google.android.gms.maps.model.LatLng
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback

class MainActivity : FragmentActivity(), OnMapReadyCallback,PermissionResultCallback, LocationUpdateInerface {
    private lateinit var checkInTimerView: CheckInTimerView
    private  var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInTimerView = findViewById(R.id.check_view)
       Loner.getClient().checkPermission(this@MainActivity, this)
    }

    override fun onPermissionGranted() {
        checkInTimerView.loadCheckInTimerComponent( true, true, 10)
        Loner.getClient().getLocationUpdate(this@MainActivity, this)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@MainActivity)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mapSettings = mMap?.uiSettings
        mapSettings?.isZoomControlsEnabled = true
        mapSettings?.isZoomGesturesEnabled = true
        mapSettings?.isScrollGesturesEnabled = true
        mapSettings?.isTiltGesturesEnabled = true
        if (mMap != null) {
            mMap?.isMyLocationEnabled = true
        }
    }

    override fun onLocationChange(location: Location?) {
        val latLng = LatLng(location?.latitude!!, location?.longitude!!)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        mMap?.animateCamera(cameraUpdate)
    }
}
