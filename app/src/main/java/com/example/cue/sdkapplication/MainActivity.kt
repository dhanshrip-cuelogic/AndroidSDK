package com.example.cue.sdkapplication


import android.Manifest

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.loner.android.sdk.widget.CheckInTimerView
import android.location.LocationManager
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AlertDialog

import android.view.ContextThemeWrapper
import com.loner.android.sdk.core.Loner


class MainActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var checkInTimerView: CheckInTimerView
    private val LOCATION_REQUEST_CODE = 101
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInTimerView = findViewById(R.id.check_view)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
       checkInTimerView.loadCheckInTimerComponent(this,true,true, 1)
    }
    private fun requestPermission(permissionType: String,
                                  requestCode: Int) {

        ActivityCompat.requestPermissions(this,
                arrayOf(permissionType), requestCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show()
                } else {
                    Loner.getClient().sendLocationUpdate(this)
                    val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mapSettings = mMap.uiSettings
        mapSettings.isZoomControlsEnabled = true
        mapSettings.isZoomGesturesEnabled = true
        mapSettings.isScrollGesturesEnabled = true
        mapSettings.isTiltGesturesEnabled = true

        if(mMap != null) {
            val permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)

            if (permission == PackageManager.PERMISSION_GRANTED) {
                val service = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)

                if (!enabled) {
                    showSettingAlert();
                }
                mMap.isMyLocationEnabled = true
            } else {
                requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_REQUEST_CODE)
            }
        }

    }


    fun showSettingAlert() {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.myDialog))

        // Set the alert dialog title
        builder.setTitle("Location On")

        // Display a message on alert dialog
        builder.setMessage("Loner SDK requires high accuracy location to ensure you get the help you need. Please select high accuracy mode in location settings.")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Setting"){dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
            dialog.dismiss()
        }
        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
