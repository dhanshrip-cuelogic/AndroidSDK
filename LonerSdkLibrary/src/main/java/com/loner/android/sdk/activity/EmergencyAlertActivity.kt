package com.loner.android.sdk.activity


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.model.VibrationManager
import com.loner.android.sdk.utils.SoundManager
import com.loner.android.sdk.viewModel.AlertActivityViewModel
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.network.networking.NetworkStatus
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_alert.*

class EmergencyAlertActivity : BaseActivity() {
    private lateinit var activityViewModel: AlertActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
        CheckInTimerView.getCheckInTimerView()?.pausedMonitorTimer()
        text_alert_name.text = getText(R.string.emergency).toString()
        activityViewModel = ViewModelProviders.of(this).get(AlertActivityViewModel::class.java)
        alertImage_resource.setImageResource(R.mipmap.emergency_alert_icon)
        SoundManager.getInstance(applicationContext).playSoundForAlert()
        VibrationManager.getInstance(applicationContext).startVibrationForAlert()
        LocationUpdate.getInstance(this).getLastLocation()
        btnAcknowledge.setOnClickListener {
            activityViewModel.sendNotification("alert_acknowledged")
        }
        activityViewModel.networkStatus.observe(this, Observer {
            it.let {
                if(it!!){
                    SoundManager.getInstance(applicationContext).stopSound()
                    val intent = Intent(this, AlertCheckInActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    finish()
                }
                VibrationManager.getInstance(applicationContext).stopVibration()
            }
        })


    }


}
