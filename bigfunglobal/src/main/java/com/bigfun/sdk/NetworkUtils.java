package com.bigfun.sdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetworkUtils {
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_MOBILE = 5;
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null) {
            return NETWORK_NONE;
        }
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }
        State state;
        NetworkInfo wifiInfo = connManager.getNetworkInfo(1);
        if (wifiInfo != null) {
            state = wifiInfo.getState();
            if (state != null && (state == State.CONNECTED || state == State.CONNECTING)) {
                return NETWORK_WIFI;
            }
        }
        NetworkInfo networkInfo = connManager.getNetworkInfo(0);
        if (networkInfo == null) {
            return NETWORK_NONE;
        }
        state = networkInfo.getState();
        String strSubTypeName = networkInfo.getSubtypeName();
        if (state == null) {
            return NETWORK_NONE;
        }
        if (state != State.CONNECTED && state != State.CONNECTING) {
            return NETWORK_NONE;
        }
        switch (activeNetInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return NETWORK_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NETWORK_3G;
            case 13:
                return NETWORK_4G;
            default:
                if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                    return NETWORK_3G;
                }
                return NETWORK_MOBILE;
        }
    }
}