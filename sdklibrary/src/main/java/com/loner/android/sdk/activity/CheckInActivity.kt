package com.loner.android.sdk.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_manual_check_in.*

class CheckInActivity : AppCompatActivity() {

    private lateinit var manualCheckInListener: ManualCheckInListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_manual_check_in)
        manualCheckInListener = CheckInTimerView.getCheckInTimerView()

        btnSendCheckin.setOnClickListener {
            manualCheckInListener.manualCheckInCompeleted(true)
            finish()
        }
        btnCancelCheckin.setOnClickListener {
            manualCheckInListener.manualCheckInCompeleted(false)
            finish()
        }

    }

}
