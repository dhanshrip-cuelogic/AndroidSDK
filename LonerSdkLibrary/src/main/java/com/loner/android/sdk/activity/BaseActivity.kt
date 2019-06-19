package com.loner.android.sdk.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.loner.android.sdk.R
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver

open class BaseActivity: AppCompatActivity(), ConnectionBroadcastReceiver.NetworkConnectionStatusListener {
    lateinit var mNetworkConnectionStatusReceivers:ConnectionBroadcastReceiver
    var networkRetryOverlay: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNetworkConnectionStatusReceivers = ConnectionBroadcastReceiver()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        networkRetryOverlay = findViewById<View>(R.id.networkOverlay)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mNetworkConnectionStatusReceivers, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        mNetworkConnectionStatusReceivers.setNetworkConnectionListener(this)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mNetworkConnectionStatusReceivers)
        mNetworkConnectionStatusReceivers.removeNetworkConnectionListener()
    }

    override fun onNetworkDisconnected() {
        networkRetryOverlay?.alpha = 0.8f
        networkRetryOverlay?.visibility = View.VISIBLE
    }

    override fun onNetworkConnected() {
        networkRetryOverlay?.visibility = View.GONE
    }
}