package com.example.cue.sdklibrary.apiModel;


import com.example.cue.sdklibrary.model.request.ActivationCode;
import com.example.cue.sdklibrary.model.response.AccessToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("/1/register")
    Call<AccessToken> register(@Body ActivationCode activationCode);
}
