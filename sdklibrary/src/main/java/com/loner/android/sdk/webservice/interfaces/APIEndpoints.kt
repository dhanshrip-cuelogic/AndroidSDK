package com.loner.android.sdk.webservice.interfaces


import com.loner.android.sdk.model.request.ActivationCode
import com.loner.android.sdk.model.response.AccessToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIEndpoints {

    @POST("/1/register")
    fun register(@Body activationCode: ActivationCode): Call<AccessToken>
}
