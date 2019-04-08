package com.example.cue.sdklibrary.Utility;

import android.content.Context;
import android.widget.Toast;

public class ToastManger {

    public static void showsMessage(Context context, String msg){

        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
