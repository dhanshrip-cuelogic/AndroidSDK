package com.example.cue.sdkapplication


import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import com.loner.android.sdk.widget.EmergencySlider
import com.loner.android.sdk.widget.EmergencySlider.EmergencySliderListetener;


class MainActivity : FragmentActivity(), OnMapReadyCallback {
    var emergencySlider: EmergencySlider? = null
    var progressBarDailog: ProgressDialog? = null
    lateinit var checkInTimerView: CheckInTimerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBarDailog = ProgressDialog(this)
        progressBarDailog!!.setMessage("Loading..")
        progressBarDailog!!.setCancelable(false)
        emergencySlider = findViewById(R.id.emergency_slider)
        checkInTimerView = findViewById(R.id.check_view)
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

    override fun onMapReady(p0: GoogleMap?) {

    }
}
