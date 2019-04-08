package com.example.cue.sdklibrary.apiModel.apis;

import com.example.cue.sdklibrary.apiModel.APIInterface;
import com.example.cue.sdklibrary.apiModel.ResponseStatusListener;
import com.example.cue.sdklibrary.apiModel.RetrofitClientInstance;
import com.example.cue.sdklibrary.model.request.ActivationCode;
import com.example.cue.sdklibrary.model.response.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegisterAPI {
    private APIInterface apiInterface;
    Call<AccessToken> callRegistration;
    public RegisterAPI() {
        this.apiInterface = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
    }

    public void registerDevice(ActivationCode activationCode, final ResponseStatusListener responseStatusListener) {
        callRegistration = apiInterface.register(activationCode);
        callRegistration.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if(response.code() == 200) {
                    AccessToken accessToken = response.body();
                    responseStatusListener.onRequestSuccess("User successfully register");

                }else{
                    responseStatusListener.onRequestSuccess("Activation code invalid");
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                responseStatusListener.onRequestFailure("Activation code invalid");
                callRegistration.cancel();
            }
        });
    }
}
