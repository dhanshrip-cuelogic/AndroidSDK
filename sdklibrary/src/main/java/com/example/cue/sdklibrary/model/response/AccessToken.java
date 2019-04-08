
package com.example.cue.sdklibrary.model.response;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

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

}
