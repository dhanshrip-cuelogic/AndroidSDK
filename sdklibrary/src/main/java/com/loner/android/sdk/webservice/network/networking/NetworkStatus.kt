package com.loner.android.sdk.webservice.network.networking

import android.content.Context
import android.net.ConnectivityManager

/**
 * This class is used to check the network status is available or not.
 */
class NetworkStatus {

    /**
     * isNetworkAvailable() method, to check the network connectivity is available or not.
     *
     * @return isNetworkAvailable either true or false.
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}