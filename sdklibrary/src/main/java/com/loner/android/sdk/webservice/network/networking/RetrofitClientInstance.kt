package com.loner.android.sdk.webservice.network.networking

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientInstance : RequestConfig {
    companion object {

        private var retrofit: Retrofit? = null

        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY

                    val certificatePinner = CertificatePinner.Builder()
                            .add(RequestConfig.Companion.HOST_NAME, "sha256/" + RequestConfig.Companion.SHA_256)
                            .build()

                    val client = OkHttpClient.Builder().addInterceptor(interceptor)
                            .certificatePinner(certificatePinner)
                            .connectTimeout(RequestConfig.Companion.TIMEOUT.toLong(), TimeUnit.MILLISECONDS).build()

                    retrofit = Retrofit.Builder()
                            .baseUrl(RequestConfig.Companion.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()
                }
                return retrofit
            }
    }
}
