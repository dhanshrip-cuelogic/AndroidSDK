package com.loner.android.sdk.webservice.network.helper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class stores all the information's related to the Errors that can occur.
 *
 * @author Swapnil Sonar
 * @version 1.0.0
 * @Date 29/12/2015
 */
public class NetworkErrorInformation extends BaseJSONParser {

    private final String KEY_STATUS_CODE = "statusCode";
    private final String KEY_ERROR = "error";
    private final String KEY_MESSAGE = "message";
    private final String KEY_DETAILS = "details";
    private final String KEY_DETAILS_CODE = "code";
    private final String KEY_DETAILS_MESSAGE = "message";

    private int statusCode;
    private String error;
    private String message;
    private int detailCode;
    private String detailMessage;

    public NetworkErrorInformation() {
        //Empty Constructor
    }

    public NetworkErrorInformation(byte[] responseData) {
        String responseString = new String(responseData);
        JSONObject jsonRegister;

        try {
            jsonRegister = new JSONObject(responseString);

            statusCode = Integer.parseInt(optInt(jsonRegister, KEY_STATUS_CODE));
            error = optString(jsonRegister, KEY_ERROR);
            message = optString(jsonRegister, KEY_MESSAGE);

            JSONObject detailObject = optJSONObject(jsonRegister, KEY_DETAILS);
            if (detailObject != null) {

                try {
                    detailCode = Integer.parseInt(optInt(detailObject, KEY_DETAILS_CODE));
                } catch (Exception e) {
                    e.printStackTrace();
                    detailCode = 0;
                }

                try {
                    detailMessage = optString(detailObject, KEY_DETAILS_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    detailMessage = "Unspecified error";
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String toString() {
        return "NetworkErrorInformation{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", detailCode=" + detailCode +
                ", detailMessage='" + detailMessage + '\'' +
                '}';
    }
}