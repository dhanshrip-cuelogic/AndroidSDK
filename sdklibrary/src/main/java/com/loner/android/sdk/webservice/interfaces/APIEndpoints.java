package com.loner.android.sdk.webservice.interfaces;


import com.loner.android.sdk.model.request.ActivationCode;
import com.loner.android.sdk.model.response.AccessToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIEndpoints {

    @POST("/1/register")
    Call<AccessToken> register(@Body ActivationCode activationCode);
}
