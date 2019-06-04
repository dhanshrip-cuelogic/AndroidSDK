package com.loner.android.sdk.activity

import android.app.Activity
import android.os.Bundle
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_missed_checkin.*

class MissedCheckInActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_missed_checkin)
        btnAcknowledge.setOnClickListener {
            CheckInTimerView.getCheckInTimerView().onCheckTimerViewUpdate()
            this@MissedCheckInActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            Loner.client.sendNotification(this,"alert_acknowledged", object :ActivityCallBackInterface{
                override fun onResponseDataSuccess(successResponse: String) {

                }

                override fun onResponseDataFailure(failureResponse: String) {

                }
            })
            finish()
        }

    }
}