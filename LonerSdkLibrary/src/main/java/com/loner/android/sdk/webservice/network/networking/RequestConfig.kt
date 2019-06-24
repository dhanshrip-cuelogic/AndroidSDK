package com.loner.android.sdk.webservice.network.networking

/**
 * This interface stored sha_256 key and host name, base url which need into API calling.
 */
interface RequestConfig {
    companion object {
        const val BASE_URL = "https://connect-staging-20.blackline-dev.com"
        //staging key
        const val SHA_256 = "yup3ktLnGP2tW4U40xlLhT6RzUPHzAlUlM8pekl+udE="
        const val HOST_NAME = "connect-staging-20.blackline-dev.com"
        // production key
        //    String SHA_256 = "OkCcMYBVBnbpc8KRJbdVytXdu1BNZy6y4jKgK97PfzY=";
        //    String HOST_NAME = "connect-live.blacklinesafety.com";
        const val TIMEOUT = 10    // Ten seconds

    }
}
