package com.loner.android.sdk.webservice.network.networking

interface RequestConfig {
    companion object {
        val BASE_URL = "https://connect-staging-20.blackline-dev.com"
        //staging key
        val SHA_256 = "yup3ktLnGP2tW4U40xlLhT6RzUPHzAlUlM8pekl+udE="
        val HOST_NAME = "connect-staging-20.blackline-dev.com"
        // production key
        //    String SHA_256 = "OkCcMYBVBnbpc8KRJbdVytXdu1BNZy6y4jKgK97PfzY=";
        //    String HOST_NAME = "connect-live.blacklinesafety.com";
        val TIMEOUT = 10    // Ten seconds

    }
}
