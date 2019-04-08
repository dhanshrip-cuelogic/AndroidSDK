package com.example.cue.sdklibrary.apiModel;

import com.example.cue.sdklibrary.apiModel.apis.RegisterAPI;
import com.example.cue.sdklibrary.model.request.ActivationCode;


public class APICommunicator {
   private RegisterAPI registerAPI;
    public void setRegistrationDevice(String accessToken, final ResponseStatusListener listener) {
        if (!accessToken.isEmpty()) {
            ActivationCode activationCode = new ActivationCode(accessToken);
            registerAPI = new RegisterAPI();
            registerAPI.registerDevice(activationCode, listener);
        } else {
            if (null != listener) listener.onRequestFailure("Empty Access Token");
        }

    }


}
