package com.example.cue.sdklibrary.apiModel;

public interface ResponseStatusListener {
    void onRequestSuccess(String response);
    void onRequestFailure(String FailureMsg);
}
