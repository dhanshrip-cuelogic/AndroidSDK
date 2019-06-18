package com.loner.android.sdk.webservice.network.apis

import com.loner.android.sdk.webservice.interfaces.APIEndpoints
import com.loner.android.sdk.webservice.network.networking.RequestConfig
import com.loner.android.sdk.webservice.network.networking.RetrofitClientInstance

/**
 * This files used as base class for other API classes.
 */
abstract class BaseRequest : RequestConfig {
    var interfaceAPI = RetrofitClientInstance.retrofitInstance!!.create(APIEndpoints::class.java)
}