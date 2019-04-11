package com.loner.android.sdk.webservice.network.helper

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * This class is used as base class for other JSON Parser classes.
 */

open class BaseJSONParser {

    /**
     * Set an Empty String if the object is null.
     */
    protected fun optString(json: JSONObject, key: String): String {
        return if (json.isNull(key)) "" else json.optString(key)
    }

    protected fun optInt(json: JSONObject, key: String): String {
        return if (json.isNull(key)) "" else "" + json.optInt(key)
    }

    protected fun optBoolean(json: JSONObject, key: String): String {
        return if (json.isNull(key)) "" else "" + json.optBoolean(key)
    }

    protected fun optJsonArray(json: JSONObject, key: String): JSONArray? {
        val jsonArray = JSONArray()
        try {
            return if (json.isNull(key)) jsonArray else json.getJSONArray(key)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }

    protected fun optJSONObject(json: JSONObject, key: String): JSONObject? {
        return if (json.isNull(key)) null else json.optJSONObject(key)
    }
}