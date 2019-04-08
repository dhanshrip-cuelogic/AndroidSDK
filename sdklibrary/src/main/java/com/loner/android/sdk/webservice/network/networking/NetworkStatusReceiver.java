package com.loner.android.sdk.webservice.network.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class detects the network status using the BroadcastReceiver component.
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    public static boolean networkStatusBool;

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkStatus networkStatus = new NetworkStatus();
        networkStatusBool = networkStatus.isNetworkAvailable(context);
    }
}
