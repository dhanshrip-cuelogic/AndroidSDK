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

open class BaseView constructor(context: Context, attrs: AttributeSet? =  null) : RelativeLayout(context, attrs) {
    protected var mNetworkConnectionStatusReceivers = ConnectionBroadcastReceiver()
    protected var gpsSettingReceiver = GPSSettingReceiver()
    private var baseView: View? = null
    private var mContext: Context? = null
    protected var baseLayout: RelativeLayout? = null
    protected var retryOverlay: View? = null
    protected var txtRetryOverlay: TextView? = null
   init {
       mContext = context
       init(context)
   }

    private fun init(context: Context) {
        baseView = inflate(context, R.layout.base_view, this)
        baseLayout = baseView?.findViewById(R.id.childContainer)
        retryOverlay = baseView?.findViewById(R.id.networkOverlay)
        txtRetryOverlay = retryOverlay?.findViewById(R.id.tvOverlay)
        registerReceiver(context)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mContext?.let { unRegisterReceiver(it) }
    }


    protected fun registerReceiver(context: Context) {
        context.registerReceiver(mNetworkConnectionStatusReceivers, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        var intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        context.registerReceiver(gpsSettingReceiver, intentFilter)
    }

    private fun unRegisterReceiver(context: Context) {
        context.unregisterReceiver(mNetworkConnectionStatusReceivers)
        mNetworkConnectionStatusReceivers.removeNetworkConnectionListener()
        context.unregisterReceiver(gpsSettingReceiver)
        gpsSettingReceiver.removeGPSSettingListener()
    }

}