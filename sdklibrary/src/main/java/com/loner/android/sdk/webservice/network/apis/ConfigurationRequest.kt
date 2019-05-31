package com.loner.android.sdk.webservice.network.apis


import android.content.Context
import com.loner.android.sdk.data.sdkconfiguraton.AppConfiguration
import com.loner.android.sdk.model.respons.ConfigurationResponse
import com.loner.android.sdk.model.respons.Fdu
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.helper.NetworkConstants
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigurationRequest(private val listener: HTTPClientInterface) : BaseRequest()  {
    private var configurationRequestCallObjet: Call<ConfigurationResponse>? = null

    fun getConfiguration(context: Context,requestObject: BaseRequest, STASK_Configuration: Int){
        configurationRequestCallObjet = interfaceAPI.getConfiguration()
        configurationRequestCallObjet!!.enqueue(object :Callback<ConfigurationResponse>{
            override fun onFailure(call: Call<ConfigurationResponse>, t: Throwable) {
                val errorInformation = NetworkErrorInformation()
                errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                listener.onFailure(requestObject, errorInformation)
                configurationRequestCallObjet!!.cancel()
            }

            override fun onResponse(call: Call<ConfigurationResponse>, response: Response<ConfigurationResponse>) {
                if (response.code() == 200) {
                    val networkSuccessInformation = NetworkSuccessInformation(STASK_Configuration)
                    var fdu: Fdu?= response.body()!!.fdu
                    var mobile = response.body()!!.mobile
                    AppConfiguration.getInstance().setFduConfiguration(context,fdu)
                    AppConfiguration.getInstance().setMobileConfiguration(context,mobile)
                    listener.onSuccess(requestObject, response, networkSuccessInformation)

                } else {
                    val errorInformation = NetworkErrorInformation()
                    errorInformation.detailMessage = NetworkConstants.FAILURE_PAYLOAD
                    listener.onFailure(requestObject, errorInformation)
                }

            }

        })


    }



}