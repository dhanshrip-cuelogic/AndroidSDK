package com.loner.android.sdk.webservice.interfaces

/**
 * This class contains all the services related method which are declared over but
 * implemented at the APIsInterface class of Service layer.
 */
interface APIsInterface {
    fun registerDevice(accessToken: String, responseDataListener: ActivityCallBackInterface)
    fun sendAlertApi(responseDataListener: ActivityCallBackInterface)
}