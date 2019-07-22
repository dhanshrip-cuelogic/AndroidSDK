package com.loner.android.sdk.activity


import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.model.VibrationManager
import com.loner.android.sdk.utils.SoundManager
import com.loner.android.sdk.webservice.network.networking.NetworkStatus
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_alert.*

class EmergencyAlertActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_alert)
        CheckInTimerView.getCheckInTimerView()?.pausedMonitorTimer()
        text_alert_name.text = getText(R.string.emergency).toString()
        alertImage_resource.setImageResource(R.mipmap.emergency_alert_icon)
        SoundManager.getInstance(applicationContext).playSoundForAlert()
        VibrationManager.getInstance(applicationContext).startVibrationForAlert()
        btnAcknowledge.setOnClickListener {
            if (NetworkStatus().isNetworkAvailable(this)) {
                Loner.getClient().sendNotification(this, "alert_acknowledged",null)
                SoundManager.getInstance(applicationContext).stopSound()
                val intent = Intent(this, AlertCheckInActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                finish()
            }
            VibrationManager.getInstance(applicationContext).stopVibration()
        }
    }


}
