package com.loner.android.sdk.webservice.network.networking;

import com.loner.android.sdk.utilities.Constant;
import com.loner.android.sdk.webservice.interfaces.APIsInterface;
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface;
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface;
import com.loner.android.sdk.webservice.models.BaseData;
import com.loner.android.sdk.webservice.network.apis.BaseRequest;
import com.loner.android.sdk.webservice.network.apis.RegisterRequest;
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation;
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation;

import java.util.HashMap;

/**
 * This files used as ServiceManager. This class is responsible to create instances
 * of different API classes and call their respective Request functions.
 * Note : This is a Singleton class.
 */
public class ServiceManager implements HTTPClientInterface, APIsInterface {

    private static ServiceManager serviceManagerInstance = null;
    private RegisterRequest registerRequest;

    private HashMap<BaseRequest, ActivityCallBackInterface> callbackMap;

    private ServiceManager() {
        callbackMap = new HashMap<BaseRequest, ActivityCallBackInterface>();
    }

    /**
     * This method returns the ServiceManager instance.
     */
    public static ServiceManager getInstance() {
        if (serviceManagerInstance == null) {
            serviceManagerInstance = new ServiceManager();
        }
        return serviceManagerInstance;
    }

    @Override
    public void onSuccess(BaseRequest responseObject, BaseData baseDataObject, NetworkSuccessInformation networkSuccessInformation) {
        ActivityCallBackInterface listener = callbackMap.get(responseObject);
        if (listener != null) {
            listener.onResponseDataSuccess(networkSuccessInformation.getResponseMsg());
            callbackMap.remove(responseObject);
        }
    }

    @Override
    public void onFailure(BaseRequest responseObject, NetworkErrorInformation errorInformation) {
        ActivityCallBackInterface listener = callbackMap.get(responseObject);
        if (listener != null) {
            listener.onResponseDataFailure(errorInformation.getError());
            callbackMap.remove(responseObject);
        }
    }

    @Override
    public void registerDevice(String accessToken, ActivityCallBackInterface responseDataListener) {
        if(!accessToken.isEmpty()) {
            registerRequest = new RegisterRequest(accessToken, this);
            callbackMap.put(registerRequest, responseDataListener);
            registerRequest.register(registerRequest, Constant.STASK_Authentication);
        }else {
         responseDataListener.onResponseDataFailure("AccessToken can not empty");
        }
    }
}