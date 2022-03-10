package com.bigfunglobal.bigfun;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.IpUtils;
import com.bigfun.sdk.utils.LocationUtils;


import java.util.HashMap;
import java.util.Map;

public class APP extends Application {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("StringFormatMatches")
    @Override
    public void onCreate() {
        super.onCreate();
//        String a=getResources().getString(R.string.talkingDateID);
//        a=String.format(a,"7BE4DF86FC804DC0B1E30C7A8FCC47FB");
        /**
         *
         * @param application 上下文 “必填”
         * @param channel 短信渠道 “必填” 短信渠道由平台提供
         * @param ChannelCode 渠道编码 "必填" 由平台提供
         */
        BigFunSDK.setDebug(true);
        BigFunSDK.getInstance().init(this, "BRWF2O2A");

    }
}
