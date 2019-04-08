package com.example.cue.sdklibrary.model.request;

import com.google.gson.annotations.SerializedName;

public class ActivationCode {
    @SerializedName("activation_code")
    public String activationCode;

    public ActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
