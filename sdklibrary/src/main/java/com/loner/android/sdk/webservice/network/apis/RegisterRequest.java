package com.loner.android.sdk.webservice.network.apis;

import com.loner.android.sdk.model.request.ActivationCode;
import com.loner.android.sdk.model.response.AccessToken;
import com.loner.android.sdk.utilities.Constant;
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface;
import com.loner.android.sdk.webservice.network.helper.NetworkConstants;
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation;
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRequest extends BaseRequest {

    private static final String TAG = RegisterRequest.class.getSimpleName();

    private HTTPClientInterface listener;
    private Call<AccessToken> callRegistration;
    private ActivationCode activationCode;

    public RegisterRequest(String accessToken, HTTPClientInterface listener) {
        this.listener = listener;
        activationCode = new ActivationCode(accessToken);
    }

    public void register(final BaseRequest requestObject, final int STASK_Authentication) {
        callRegistration = interfaceAPI.register(activationCode);
        callRegistration.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() == 200) {
                    AccessToken accessToken = response.body();
                    NetworkSuccessInformation networkSuccessInformation = new NetworkSuccessInformation(STASK_Authentication);
                    listener.onSuccess(requestObject, accessToken, networkSuccessInformation);
                } else {
                    NetworkErrorInformation errorInformation = new NetworkErrorInformation();
                    errorInformation.setDetailMessage(NetworkConstants.FAILURE_PAYLOAD);
                    listener.onFailure(requestObject, errorInformation);
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                NetworkErrorInformation errorInformation = new NetworkErrorInformation();
                errorInformation.setDetailMessage(NetworkConstants.FAILURE_PAYLOAD);
                listener.onFailure(requestObject, errorInformation);
                callRegistration.cancel();
            }
        });
    }
}