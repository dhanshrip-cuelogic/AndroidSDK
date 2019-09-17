package com.loner.android.sdk.widget

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.loner.android.sdk.R
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver
import com.loner.android.sdk.receiver.GPSSettingReceiver

open class BaseView : RelativeLayout,ConnectionBroadcastReceiver.NetworkConnectionStatusListener, GPSSettingReceiver.GPSSettingListener  {
    private var mNetworkConnectionStatusReceivers = ConnectionBroadcastReceiver()
    private var gpsSettingReceiver = GPSSettingReceiver()
    private var baseView: View? = null
    private var mContext: Context? = null

    protected var baseLayout: RelativeLayout? = null
    protected var retryOverlay: View? = null
    private var txtRetryOverlay: TextView? = null

    constructor(context: Context) : super(context){
       mContext = context
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        mContext = context
        init(context)
    }


    private fun init(context: Context) {
        baseView = inflate(context,R.layout.base_view, this)
        baseLayout = baseView?.findViewById(R.id.childContainer)
        retryOverlay = baseView?.findViewById(R.id.networkOverlay)
        txtRetryOverlay = retryOverlay?.findViewById(R.id.tvOverlay)
        registerReceiver(context)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mContext?.let { unRegisterReceiver(it) }
    }

    override fun onNetworkDisconnected() {
        txtRetryOverlay?.text = "Waiting to reconnect"
    }

    override fun onNetworkConnected() {
    }

    override fun onGPSDisabled(context: Context) {
        txtRetryOverlay?.text = "Location service is disabled"
    }

    override fun onGPSEnabled(context: Context) {

    }

    private fun registerReceiver(context: Context){
        context.registerReceiver(mNetworkConnectionStatusReceivers, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        mNetworkConnectionStatusReceivers.setNetworkConnectionListener(this)

        var intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        context.registerReceiver(gpsSettingReceiver,intentFilter)
        gpsSettingReceiver.setGPSSettingListener(this)
    }

    private fun unRegisterReceiver(context: Context){
        context.unregisterReceiver(mNetworkConnectionStatusReceivers)
        mNetworkConnectionStatusReceivers.removeNetworkConnectionListener()

        context.unregisterReceiver(gpsSettingReceiver)
        gpsSettingReceiver.removeGPSSettingListener()
    }

}