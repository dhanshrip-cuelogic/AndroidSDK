package com.example.cue.sdklibrary;

import android.support.annotation.Nullable;
import com.example.cue.sdklibrary.apiModel.APICommunicator;
import com.example.cue.sdklibrary.apiModel.APIInterface;
import com.example.cue.sdklibrary.apiModel.ResponseStatusListener;
import com.example.cue.sdklibrary.apiModel.RetrofitClientInstance;


public class Loner {

    private static @Nullable Loner instance;
    private APICommunicator apiCommunicator;
    public static synchronized Loner getClient(){
        if(instance == null){
            instance = new Loner();
        }
        return instance;
    }

    private Loner() {
        apiCommunicator = new APICommunicator();
    }


    public synchronized APICommunicator Api(){
        return apiCommunicator;
    }


}
