package com.example.cue.sdklibrary.apiModel;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://connect-staging-20.blackline-dev.com";
    // Staging key
    private static final String sha_256 = "yup3ktLnGP2tW4U40xlLhT6RzUPHzAlUlM8pekl+udE=";
    private static final String hostname = "connect-staging-20.blackline-dev.com";
    // Production key
  //  private static final String sha_256 = "OkCcMYBVBnbpc8KRJbdVytXdu1BNZy6y4jKgK97PfzY=";
  //  private static final String hostname = "connect-live.blacklinesafety.com";
    private static final int TIMEOUT = 60000;


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(hostname, "sha256/"+sha_256)
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
