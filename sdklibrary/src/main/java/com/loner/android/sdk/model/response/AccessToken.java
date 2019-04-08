
package com.loner.android.sdk.model.response;

import com.loner.android.sdk.webservice.models.BaseData;
import com.google.gson.annotations.SerializedName;

public class AccessToken extends BaseData {

    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("access_token")
    private String accessToken;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public String toString() {
        return "AccessToken{" +
                "deviceId='" + deviceId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
