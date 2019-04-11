package com.loner.android.sdk.webservice.network.networking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * This class detects the network status using the BroadcastReceiver component.
 */
class NetworkStatusReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val networkStatus = NetworkStatus()
        networkStatusBool = networkStatus.isNetworkAvailable(context)
    }

    companion object {

        var networkStatusBool: Boolean = false
    }
}
