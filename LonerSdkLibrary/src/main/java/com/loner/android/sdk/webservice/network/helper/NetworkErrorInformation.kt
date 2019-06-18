package com.loner.android.sdk.webservice.network.helper

import org.json.JSONException
import org.json.JSONObject


/**
 * This class stores all the information's related to the Errors that can occur.
 */
class NetworkErrorInformation : BaseJSONParser {

    private val KEY_STATUS_CODE = "statusCode"
    private val KEY_ERROR = "error"
    private val KEY_MESSAGE = "message"
    private val KEY_DETAILS = "details"
    private val KEY_DETAILS_CODE = "code"
    private val KEY_DETAILS_MESSAGE = "message"

    private var statusCode: Int = 0
    private var error: String? = null
    var message: String? = null
    private var detailCode: Int = 0
    var detailMessage: String? = null

    constructor() {
        //Empty Constructor
    }

    constructor(responseData: ByteArray) {
        val responseString = String(responseData)
        val jsonRegister: JSONObject

        try {
            jsonRegister = JSONObject(responseString)

            statusCode = Integer.parseInt(optInt(jsonRegister, KEY_STATUS_CODE))
            error = optString(jsonRegister, KEY_ERROR)
            message = optString(jsonRegister, KEY_MESSAGE)

            val detailObject = optJSONObject(jsonRegister, KEY_DETAILS)
            if (detailObject != null) {

                try {
                    detailCode = Integer.parseInt(optInt(detailObject, KEY_DETAILS_CODE))
                } catch (e: Exception) {
                    e.printStackTrace()
                    detailCode = 0
                }

                try {
                    detailMessage = optString(detailObject, KEY_DETAILS_MESSAGE)
                } catch (e: Exception) {
                    e.printStackTrace()
                    detailMessage = "Unspecified error"
                }

            }

        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun toString(): String {
        return "NetworkErrorInformation{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\''.toString() +
                ", message='" + message + '\''.toString() +
                ", detailCode=" + detailCode +
                ", detailMessage='" + detailMessage + '\''.toString() +
                '}'.toString()
    }
}