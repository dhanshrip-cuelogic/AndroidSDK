package com.loner.android.sdk.webservice.interfaces

import android.content.Context

/**
 * This class contains all the services related method which are declared over but
 * implemented at the APIsInterface class of Service layer.
 */
interface APIsInterface {
    fun sendAlertApi(context: Context,message:String, responseDataListener: ActivityCallBackInterface)
    fun requestConfiguration(context:Context,responseDataListener: ActivityCallBackInterface)
    fun sendMessageApi(context: Context, message:String,responseDataListener: ActivityCallBackInterface)
    fun sendNotificationApi(context: Context, message:String,responseDataListener: ActivityCallBackInterface)



}