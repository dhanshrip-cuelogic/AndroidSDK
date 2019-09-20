package com.loner.android.sdk.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import com.loner.android.sdk.R
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.dailogs.LonerDialogListener
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver
import com.loner.android.sdk.receiver.GPSSettingReceiver

open class BaseActivity: AppCompatActivity(), ConnectionBroadcastReceiver.NetworkConnectionStatusListener,GPSSettingReceiver.GPSSettingListener {
    private lateinit var mNetworkConnectionStatusReceivers:ConnectionBroadcastReceiver
    private lateinit var gpsSettingReceiver:GPSSettingReceiver

    private var networkRetryOverlay: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        mNetworkConnectionStatusReceivers = ConnectionBroadcastReceiver()
        gpsSettingReceiver = GPSSettingReceiver()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        networkRetryOverlay = findViewById<View>(R.id.networkOverlay)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
       unRegisterReceiver()
    }

    override fun onNetworkDisconnected() {
        networkRetryOverlay?.alpha = 0.8f
        networkRetryOverlay?.visibility = View.VISIBLE
    }

    override fun onNetworkConnected() {
        networkRetryOverlay?.visibility = View.GONE
    }

    override fun onGPSDisabled(context: Context) {
        showSettingAlertMessage(context,"Location service is disabled","Seems like your GPS is disabled, Loner requires GPS is to be enable in order to send your location in case of emergency.")
    }

    override fun onGPSEnabled(context: Context) {

    }

    private fun registerReceiver(){
        registerReceiver(mNetworkConnectionStatusReceivers, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        mNetworkConnectionStatusReceivers.setNetworkConnectionListener(this)

        var intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        registerReceiver(gpsSettingReceiver,intentFilter)
        gpsSettingReceiver.setGPSSettingListener(this)
    }

    private fun unRegisterReceiver(){
        unregisterReceiver(mNetworkConnectionStatusReceivers)
        mNetworkConnectionStatusReceivers.removeNetworkConnectionListener()

        unregisterReceiver(gpsSettingReceiver)
        gpsSettingReceiver.removeGPSSettingListener()
    }

    /**
     * Show Alert dialog to show usage of permission
     * @param title String indicates title of the dialog
     * @param message String indicates the usage of location
     */
    private fun showSettingAlertMessage(context: Context, title: String, message: String?) {
        if(!isFinishing && !LonerDialog.getInstance().isShowingAlert()) {
            LonerDialog.getInstance().showAlertDialog(context, title, message, null, object : LonerDialogListener {
                override fun onPositiveButtonClicked() {

                }
            })
        }
    }

}