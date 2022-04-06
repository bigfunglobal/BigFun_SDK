package com.bigfun.sdk.NetWork;

import static com.bigfun.sdk.BigFunSDK.TAG;
import static com.bigfun.sdk.BigFunSDK.mContext;
import static com.bigfun.sdk.BigFunSDK.onEvent;

import android.util.Log;

import com.bigfun.sdk.LogUtils;
import com.bigfun.sdk.model.ISPlacement;
import com.bigfun.sdk.model.TMISPlacement;
import com.goldsource.sdk.GoldSource;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

import java.util.HashMap;
import java.util.Map;


public class TMNetWork {

    public TMNetWork() {

    }

    private static TMNetWork instance;

    public static TMNetWork getInstance() {

        if (instance == null) {
            synchronized (TMNetWork.class) {
                if (instance == null) {
                    instance = new TMNetWork();
                }
            }
        }
        return instance;
    }

    private static BFTMRewardedVideoListener listener;

    public static void init() {
        GoldSource.setInterstitialListener(interstitialListener);
        GoldSource.setRewardedVideoListener(rewardedVideoListener);
    }
//
//
    public static void showRewardedVideo(BFTMRewardedVideoListener bflistener) {
        listener = bflistener;
        GoldSource.showRewardedVideo();
    }
    public static boolean showRewardedVideo() {

        return GoldSource.showRewardedVideo();
    }
//
    public static boolean showInterstitial() {

        return GoldSource.showInterstitial();
    }


    //----------插页-----------
    static InterstitialListener interstitialListener = new InterstitialListener() {
        @Override
        public void onInterstitialAdReady() {
            LogUtils.log("onInterstitialAdReady");
        }

        @Override
        public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
            LogUtils.log("onInterstitialAdLoadFailed(" + ironSourceError.getErrorCode() + "：" + ironSourceError.getErrorMessage() + ")");
        }

        @Override
        public void onInterstitialAdOpened() {
            LogUtils.log("onInterstitialAdOpened");
        }

        @Override
        public void onInterstitialAdClosed() {
            LogUtils.log("onInterstitialAdClosed");
        }

        @Override
        public void onInterstitialAdShowSucceeded() {
            LogUtils.log("onInterstitialAdShowSucceeded");
        }

        @Override
        public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
            LogUtils.log("onInterstitialAdShowFailed(" + ironSourceError.getErrorCode() + "：" + ironSourceError.getErrorMessage() + ")");
        }

        @Override
        public void onInterstitialAdClicked() {
            LogUtils.log("onInterstitialAdClicked");
        }
    };


    //--------奖励视屏-------------
    static RewardedVideoListener rewardedVideoListener = new RewardedVideoListener() {
        @Override
        public void onRewardedVideoAdOpened() {
            LogUtils.log("onRewardedVideoAdOpened");
            if(listener!=null)
            listener.onRewardedVideoAdOpened();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            //视频关闭时调用
            LogUtils.log("onRewardedVideoAdClosed");
            //这里我们向用户显示一个对话框，如果他得到了奖励
            if(listener!=null)
            listener.onRewardedVideoAdClosed();
        }

        @Override
        public void onRewardedVideoAvailabilityChanged(boolean b) {
            LogUtils.log("onRewardedVideoAvailabilityChanged");
            if(listener!=null)
            listener.onRewardedVideoAvailabilityChanged(b);
        }

        @Override
        public void onRewardedVideoAdStarted() {
            LogUtils.log("onRewardedVideoAdStarted");
            if(listener!=null)
            listener.onRewardedVideoAdStarted();
        }

        @Override
        public void onRewardedVideoAdEnded() {
            LogUtils.log("onRewardedVideoAdEnded");
            if(listener!=null)
            listener.onRewardedVideoAdEnded();
        }

        @Override
        public void onRewardedVideoAdRewarded(Placement placement) {
            //当视频得到奖励并且可以给用户奖励时调用
            LogUtils.log("onRewardedVideoAdRewarded" + " " + placement);
            if(listener!=null)
            listener.onRewardedVideoAdRewarded(new TMISPlacement(placement));
        }

        @Override
        public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
            LogUtils.log("onRewardedVideoAdShowFailed(" + ironSourceError.getErrorCode() + "：" + ironSourceError.getErrorMessage() + ")");
            if(listener!=null)
            listener.onRewardedVideoAdShowFailed(ironSourceError.getErrorCode()+"_"+ironSourceError.getErrorMessage());
        }

        @Override
        public void onRewardedVideoAdClicked(Placement placement) {
            LogUtils.log("onRewardedVideoAdClicked(" + placement.getPlacementId() + ")");
            if(listener!=null)
            listener.onRewardedVideoAdClicked(new TMISPlacement(placement));
        }
    };

}
