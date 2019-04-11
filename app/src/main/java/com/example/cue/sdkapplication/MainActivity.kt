package com.example.cue.sdkapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.loner.android.sdk.activity.EmergencyAlert
import com.loner.android.sdk.utilities.Toaster


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val emergencyAlert = findViewById<EmergencyAlert>(R.id.emergency)

        emergencyAlert.setOnEmergencyAlert(object : EmergencyAlert.OnEmergencyClickListioner {
            override fun onClick() {
                Toaster.showShort(this@MainActivity, "This is Emergency Alert")
            }
        })
    }
}
