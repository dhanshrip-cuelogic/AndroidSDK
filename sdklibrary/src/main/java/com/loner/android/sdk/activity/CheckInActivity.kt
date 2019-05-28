package com.loner.android.sdk.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import kotlinx.android.synthetic.main.activity_manual_check_in.*

class CheckInActivity : AppCompatActivity() {

    companion object {
        lateinit var instance:CheckInActivity
        fun setCheckinInstance(instance : CheckInActivity) {
            this.instance = instance
        }
        fun  getCheckinInstance():CheckInActivity {
            return instance!!
        }
    }
    var manualCheckInListener:ManualCheckInListener? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()!!.hide()
      /* this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen*/
        setContentView(R.layout.activity_manual_check_in)
        setCheckinInstance(this)

        btnSendCheckin.setOnClickListener {

        }
        btnCancelCheckin.setOnClickListener {

        }

    }

    fun setManualCheckListener(manualCheckInListener: ManualCheckInListener?){
        this.manualCheckInListener = manualCheckInListener
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
