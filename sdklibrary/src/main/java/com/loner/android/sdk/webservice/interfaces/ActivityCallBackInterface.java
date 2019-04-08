package com.loner.android.sdk.webservice.interfaces;

import com.loner.android.sdk.webservice.models.BaseData;
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation;

/**
 * This class will act as a interface to listen the response from the server
 * between the service manager class, from the service layer and any of the view controller
 * from the view and the requesting activity will always implements this interface.
 */
public interface ActivityCallBackInterface {

    /**
     * Will trigger when the request is successful.
     */
    void onResponseDataSuccess(BaseData baseData);

    /**
     * Will trigger when the request has failed.
     */
    void onResponseDataFailure(NetworkErrorInformation errorInformation);
}
