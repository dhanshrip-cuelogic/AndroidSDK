package com.example.cue.sdkapplication


import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import com.loner.android.sdk.widget.EmergencySlider
import com.loner.android.sdk.widget.EmergencySlider.EmergencySliderListetener;
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng




class MainActivity : FragmentActivity(), OnMapReadyCallback {
    var emergencySlider: EmergencySlider? = null
    var progressBarDailog: ProgressDialog? = null
    lateinit var checkInTimerView: CheckInTimerView
    private val LOCATION_REQUEST_CODE = 101
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBarDailog = ProgressDialog(this)
        progressBarDailog!!.setMessage("Loading..")
        progressBarDailog!!.setCancelable(false)
        emergencySlider = findViewById(R.id.emergency_slider)
        checkInTimerView = findViewById(R.id.check_view)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkInTimerView.loadCheckInTimerComponent(true,true, 1, object: CheckInTimerView.OnTimerListener {
            override fun onTimerComplete() {
                showCheckAlert()
            }
        })



        // use Loner sdk for slider
        emergencySlider?.setOnEmergencySliderListetener(object : EmergencySliderListetener {
            override fun onEmergencySlide() {
                    progressBarDailog!!.show()
                    emergencyAlertCall()
            }

        })
    }

    fun showCheckAlert(){
        Loner.client.showCheckInAlertDialog(this, getText(com.loner.android.sdk.R.string.check_in_required).toString(),
                getText(com.loner.android.sdk.R.string.press_ok_to_check_in_now).toString(),null)
    }
    fun emergencyAlertCall() {
        // Call alert send api call from loner sdk
        Loner.client.sendEmergencyAlertApi(this, object : ActivityCallBackInterface {
            override fun onResponseDataSuccess(successResponse: String) {
                progressBarDailog!!.dismiss()
                alertResponseDialog(successResponse)
            }
            override fun onResponseDataFailure(failureResponse: String) {
                progressBarDailog!!.dismiss()
                alertResponseDialog(failureResponse)
            }

        })
    }

    fun alertResponseDialog(alertMsg: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        // set message of alert dialog
        dialogBuilder.setMessage(alertMsg.trim())
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert Response")
        // show alert dialog
        alert.show()
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

                    val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if(mMap != null) {
            val permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap?.isMyLocationEnabled = true
            } else {
                requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_REQUEST_CODE)
            }
        }

    }
}
