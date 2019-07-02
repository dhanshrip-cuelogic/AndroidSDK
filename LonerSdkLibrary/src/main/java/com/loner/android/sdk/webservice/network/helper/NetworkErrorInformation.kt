package com.loner.android.sdk.webservice.network.helper

import org.json.JSONException
import org.json.JSONObject


/**
 * This class stores all the information's related to the Errors that can occur.
 */
class NetworkErrorInformation : BaseJSONParser {

    private val STATUS = "statusCode"
    private val KEYERROR = "error"
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

            statusCode = Integer.parseInt(optInt(jsonRegister, STATUS))
            error = optString(jsonRegister, KEYERROR)
            message = optString(jsonRegister, KEY_MESSAGE)

            val detailObject = optJSONObject(jsonRegister, KEY_DETAILS)
            if (detailObject != null) {

                detailCode = try {
                    Integer.parseInt(optInt(detailObject, KEY_DETAILS_CODE))
                } catch (e: Exception) {
                    e.printStackTrace()
                    0
                }

                detailMessage = try {
                    optString(detailObject, KEY_DETAILS_MESSAGE)
                } catch (e: Exception) {
                    e.printStackTrace()
                    "Unspecified error"
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