package com.loner.android.sdk.webservice.network.apis;

import com.loner.android.sdk.webservice.interfaces.APIEndpoints;
import com.loner.android.sdk.webservice.network.networking.RetrofitClientInstance;
import com.loner.android.sdk.webservice.network.networking.RequestConfig;

/**
 * This files used as base class for other API classes.
 */
public abstract class BaseRequest implements RequestConfig {
    public APIEndpoints interfaceAPI = RetrofitClientInstance.getRetrofitInstance().create(APIEndpoints.class);
}