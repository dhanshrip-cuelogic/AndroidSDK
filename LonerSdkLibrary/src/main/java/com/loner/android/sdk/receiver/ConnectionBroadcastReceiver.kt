package com.loner.android.sdk.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

class ConnectionBroadcastReceiver: BroadcastReceiver()  {
     private var mNetworkConnectionStatusListener:NetworkConnectionStatusListener? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetInfo: NetworkInfo? = null
        if (connectivityManager != null)
            activeNetInfo = connectivityManager.activeNetworkInfo

        if (activeNetInfo != null && activeNetInfo.isConnected) {
            Log.d("Network_state", "Mobile connected:")
             mNetworkConnectionStatusListener?.onNetworkConnected()
        } else {
            Log.d("Network_state", "Mobile Disconnected:")
            mNetworkConnectionStatusListener?.onNetworkDisconnected()
        }
    }

    interface NetworkConnectionStatusListener {
        fun onNetworkDisconnected()
        fun onNetworkConnected()
    }

     fun setNetworkConnectionListener(networkConnectionStatusListener: NetworkConnectionStatusListener){
        this.mNetworkConnectionStatusListener = networkConnectionStatusListener
    }
     fun removeNetworkConnectionListener(){
        mNetworkConnectionStatusListener = null
     }
}