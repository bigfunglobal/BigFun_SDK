package com.bigfun.sdk;

import android.util.Log;

import com.adjust.sdk.AdjustConfig;

public class LogUtils {
    private static final String TAG = "BigFunSDK>>>>";

    private LogUtils() {

    }

    public static void log(String message) {
        if (!BigFunSDK.isDebug) {
            return;
        }
         Log.d(TAG, "" + message);
    }
}
