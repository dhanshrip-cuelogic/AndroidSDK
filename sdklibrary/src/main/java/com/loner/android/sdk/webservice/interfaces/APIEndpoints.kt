package com.loner.android.sdk.webservice.interfaces


import com.loner.android.sdk.model.request.RequestAlert
import com.loner.android.sdk.model.request.RequestMessageApi
import com.loner.android.sdk.model.respons.ConfigurationResponse
import com.loner.android.sdk.utils.Constant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIEndpoints {
    @POST("/1/alert?access_token=" + Constant.accessToken)
    fun alert(@Body requestAlert: RequestAlert): Call<Void>

    @GET("/1/configuration/${Constant.deviceId}?access_token=" + Constant.accessToken)
    fun getConfiguration():Call<ConfigurationResponse>

    @POST("/1/message?access_token=" + Constant.accessToken)
    fun message(@Body messageApi: RequestMessageApi): Call<Void>

}
