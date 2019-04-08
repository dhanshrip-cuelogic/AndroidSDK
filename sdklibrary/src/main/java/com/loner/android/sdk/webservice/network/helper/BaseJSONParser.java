package com.loner.android.sdk.webservice.network.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used as base class for other JSON Parser classes.
 */

public class BaseJSONParser {

    /**
     * Set an Empty String if the object is null.
     */
    protected String optString(final JSONObject json, final String key) {
        return json.isNull(key) ? "" : json.optString(key);
    }

    protected String optInt(final JSONObject json, final String key) {
        return json.isNull(key) ? "" : ""+json.optInt(key);
    }

    protected String optBoolean(final JSONObject json, final String key) {
        return json.isNull(key) ? "" : ""+json.optBoolean(key);
    }

    protected JSONArray optJsonArray(final JSONObject json, final String key) {
        JSONArray jsonArray =  new JSONArray();
        try {
            return json.isNull(key) ? jsonArray : json.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return null;
    }

    protected JSONObject optJSONObject(final JSONObject json, final String key) {
        return json.isNull(key) ? null : json.optJSONObject(key);
    }
}