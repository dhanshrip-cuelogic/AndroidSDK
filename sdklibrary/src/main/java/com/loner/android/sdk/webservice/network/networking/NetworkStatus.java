package com.loner.android.sdk.webservice.network.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class is used to check the network status is available or not.
 */
public class NetworkStatus {

    /**
     * isNetworkAvailable() method, to check the network connectivity is available or not.
     *
     * @return isNetworkAvailable either true or false.
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}