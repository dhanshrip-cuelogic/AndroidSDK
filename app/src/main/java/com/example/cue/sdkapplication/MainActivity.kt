package com.example.cue.sdkapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window

import android.widget.Toast
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.EmergencySlider

class MainActivity : Activity() {
    var emergencySlider: EmergencySlider? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emergencySlider = findViewById(R.id.emergency_slider)
        emergencySlider?.setOnEmergencySliderListetener(object : ActivityCallBackInterface {
            override fun onResponseDataSuccess(successResponse: String) {
                alertResponseDialog(successResponse)

            }

            override fun onResponseDataFailure(failureResponse: String) {
                alertResponseDialog(failureResponse)
            }
        })

    }



    fun alertResponseDialog(alertMsg:String){
        val dialogBuilder = AlertDialog.Builder(this)
        // set message of alert dialog
        dialogBuilder.setMessage(alertMsg.trim())
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
                })


        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert Response")
        // show alert dialog
        alert.show()
    }

}
