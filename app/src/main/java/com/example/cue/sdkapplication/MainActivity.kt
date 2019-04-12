package com.example.cue.sdkapplication

import android.app.Activity
import android.os.Bundle
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
                Toast.makeText(applicationContext, successResponse, Toast.LENGTH_LONG).show()
            }

            override fun onResponseDataFailure(failureResponse: String) {
                Toast.makeText(applicationContext, failureResponse, Toast.LENGTH_LONG).show()
            }
        })



    }
}
