package com.bigfunglobal.bigfun;

import static com.bigfun.sdk.utils.FunctionUtils.isAppAlive;
import static com.bigfun.sdk.utils.Utils.isNetSystemUsable;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.adjust.sdk.AdjustAttribution;
import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.listener.BFAdjustListener;
import com.bigfun.sdk.listener.BFSuccessListener;


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

//        Log.e("sadda",""+isNetSystemUsable(this));
        BigFunSDK.init(this, "BRWF2O2A", new BFAdjustListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                Log.e("asdwa",attribution.network+"_"+attribution.campaign);
            }
        }, new BFSuccessListener() {
            @Override
            public void onSuccess() {
                Log.e("asdasda", "213123123");
            }
        });
        BigFunSDK.setDebug(true);
//        Log.e("asdasds",isAppAlive(this)+"");
    }
}
