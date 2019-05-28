package com.example.cue.sdkapplication


import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import com.loner.android.sdk.widget.EmergencySlider
import com.loner.android.sdk.widget.EmergencySlider.EmergencySliderListetener;


class MainActivity : Activity(){
    var emergencySlider: EmergencySlider? = null
    var progressBarDailog: ProgressDialog? = null
    lateinit var checkInTimerView: CheckInTimerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBarDailog = ProgressDialog(this)
        progressBarDailog!!.setMessage("Loading..")
        progressBarDailog!!.setCancelable(true)
        emergencySlider = findViewById(R.id.emergency_slider)
        checkInTimerView = findViewById(R.id.check_view)
        checkInTimerView.loadChecKInTimerComponent(true,true, 120)


        // use Loner sdk for slider
        emergencySlider?.setOnEmergencySliderListetener(object : EmergencySliderListetener {
            override fun onEmergencySlide() {
                    progressBarDailog!!.show()
                    emergencyAlertCall()
            }

        })
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

}
