package com.loner.android.sdk.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import kotlinx.android.synthetic.main.activity_alert.*

class EmergencyAlertActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_alert)
        txtalert_name.text = getText(R.string.emergency).toString()
        alertImage_resource.setImageResource(R.mipmap.emergency_alert_icon)
        btnAcknowledge.setOnClickListener {
            Loner.client.sendNotification(this,"alert_acknowledged", object : ActivityCallBackInterface {
                override fun onResponseDataSuccess(successResponse: String) {

                }

                override fun onResponseDataFailure(failureResponse: String) {

                }
            })
         finish()
        }
    }
}
