package com.loner.android.sdk.webservice.network.networking

import android.content.Context
import android.location.Location
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.webservice.interfaces.APIsInterface
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.interfaces.HTTPClientInterface
import com.loner.android.sdk.webservice.network.apis.*
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation
import com.loner.android.sdk.webservice.network.helper.NetworkSuccessInformation
import java.util.*

/**
 * <p>This files used as ServiceManager. This class is responsible to create instances
 * of different API classes and call their respective Request functions.
 * Note : This is a Singleton class.</p>
 */
class ServiceManager private constructor() : HTTPClientInterface, APIsInterface {
    private var alertRequest: AlertRequest? = null
    private var configurationRequest: ConfigurationRequest? = null
    private var messageRequest :MessageRequest? = null
    private var notificationRequest: NotificationRequest? = null
    private var locationUpdate: LocationUpdate? = null

    private val callbackMap: HashMap<BaseRequest, ActivityCallBackInterface>? = HashMap()
    private var networkStatus: NetworkStatus = NetworkStatus()

    /**
     * <p>This method of  HTTPClientInterface
     * When the request api returns the success state 200 status code.</p>
     * @param responseObject this object provide call back object related API call classes.
     * @param baseDataObject it's provide a response data class class object
     * @param networkSuccessInformation it is provide success response message as per API call.
     */
    override fun onSuccess(responseObject: BaseRequest, baseDataObject: Any, networkSuccessInformation: NetworkSuccessInformation) {
            callbackMap?.get(responseObject)?.onResponseDataSuccess(networkSuccessInformation.responseMsg!!)
            callbackMap?.remove(responseObject)

    }
    /**<p>This method of  HTTPClientInterface
     * When the request api returns the failure.</p>
     * @param responseObject this object provide call back object related API call classes.
     * @param errorInformation this object provide error message related api.
     */
    override fun onFailure(responseObject: BaseRequest, errorInformation: NetworkErrorInformation) {
            callbackMap?.get(responseObject)?.onResponseDataFailure(errorInformation.detailMessage!!)
            callbackMap?.remove(responseObject)

    }

    /**
     * <p>This method of  APIsInterface </p>
     * This method create a object AlertRequest class.
     * @param responseDataListener this interface return a callback of API response
     */
    override fun sendAlertApi(context:Context,message: String,responseDataListener: ActivityCallBackInterface?) {
        if(networkStatus.isNetworkAvailable(context)) {
            alertRequest = AlertRequest(message,this)
            responseDataListener?.let {  callbackMap?.set(alertRequest as BaseRequest, responseDataListener)}
            alertRequest!!.alert(alertRequest!!, Constant.STASK_AlertEmergency)
        }else {
            responseDataListener?.onResponseDataFailure("Please check Internet Connection")
        }

    }

    override fun requestConfiguration(context: Context, responseDataListener: ActivityCallBackInterface?) {
        if(networkStatus.isNetworkAvailable(context)) {
            configurationRequest = ConfigurationRequest(this)
            responseDataListener?.let { callbackMap?.set(configurationRequest as BaseRequest, responseDataListener) }
            configurationRequest!!.getConfiguration(context,configurationRequest!!, Constant.STASK_Configuration)
        }else {
            responseDataListener?.onResponseDataFailure("Please check Internet Connection")
        }

    }

    override fun sendMessageApi(context: Context, message: String, responseDataListener: ActivityCallBackInterface?) {
        if(networkStatus.isNetworkAvailable(context) && message.isNotEmpty()){
            messageRequest = MessageRequest(message,this)
            responseDataListener?.let { callbackMap?.set(messageRequest as BaseRequest, responseDataListener) }
            messageRequest?.messageToServer(messageRequest!!,Constant.STASK_MESSAGE)
        } else if(message.isEmpty()){
            responseDataListener?.onResponseDataFailure("Message should not be empty")
        } else {
            responseDataListener?.onResponseDataFailure("Please check Internet Connection")
        }

    }

    override fun sendNotificationApi(context: Context, message: String, responseDataListener: ActivityCallBackInterface?) {
        if(networkStatus.isNetworkAvailable(context) && message.isNotEmpty()){
            notificationRequest = NotificationRequest(message,this)
            responseDataListener?.let { callbackMap?.set(notificationRequest as BaseRequest, responseDataListener) }
            notificationRequest?.notificationToServer(notificationRequest!!,Constant.STASK_NOTIFICATION)
        } else if(message.isEmpty()){
            responseDataListener?.onResponseDataFailure("Message should not be empty")
        } else {
            responseDataListener?.onResponseDataFailure("Please check Internet Connection")
        }
    }

    override fun sendLocationApi(context: Context, mLocation: Location?, responseDataListener: ActivityCallBackInterface?) {
        if(networkStatus.isNetworkAvailable(context)) {
           locationUpdate = LocationUpdate(mLocation,this)
            responseDataListener?.let { callbackMap?.set(locationUpdate as BaseRequest, responseDataListener) }
            locationUpdate?.sendLocation(locationUpdate!!,Constant.STASK_LOCATION)
        }else {
            responseDataListener?.onResponseDataFailure("Please check Internet Connection")
        }
    }

    companion object {
        @Volatile
        private var serviceManagerInstance: ServiceManager? = null

        fun getInstance(): ServiceManager {
            return serviceManagerInstance ?: synchronized(this) {
                ServiceManager().also {
                    serviceManagerInstance = it
                }
            }
        }
    }
}