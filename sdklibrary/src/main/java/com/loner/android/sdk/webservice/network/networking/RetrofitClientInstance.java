package com.loner.android.sdk.webservice.network.networking;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance implements RequestConfig {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(HOST_NAME, "sha256/" + SHA_256)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .certificatePinner(certificatePinner)
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
