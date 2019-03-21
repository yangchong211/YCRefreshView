package org.yczbj.ycrefreshviewlib.utils;

import android.util.Log;


public class RefreshLogUtils {

    private static final String TAG = "RefreshLogUtils";
    public static boolean isLog = true;

    public static void d(String message) {
        if(isLog){
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if(isLog){
            Log.i(TAG, message);
        }

    }

    public static void e(String message) {
        if(isLog){
            Log.e(TAG, message);
        }
    }


    public static void e(String message, Throwable throwable) {
        if(isLog){
            Log.e(TAG, message, throwable);
        }
    }

}
