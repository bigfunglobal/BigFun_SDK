package com.bigfun.sdk.NetWork;

import static com.bigfun.sdk.BigFunSDK.TAG;
import static com.bigfun.sdk.BigFunSDK.mContext;
import static com.bigfun.sdk.BigFunSDK.onEvent;

import android.util.Log;

import com.bigfun.sdk.LogUtils;
import com.bigfun.sdk.model.ISPlacement;
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

    private static BFRewardedVideoListener listener;

    public static void init() {
        GoldSource.setInterstitialListener(interstitialListener);
        GoldSource.setRewardedVideoListener(rewardedVideoListener);
    }
//
//
    public static void showRewardedVideo(BFRewardedVideoListener bflistener) {
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "TigerMedia");
        onEvent(mContext, "BFAd_TM_RewardedVideo", map);
        listener = bflistener;
        GoldSource.showRewardedVideo();
    }
    public static void showRewardedVideo() {
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "TigerMedia");
        onEvent(mContext, "BFAd_TM_RewardedVideo", map);
        GoldSource.showRewardedVideo();
    }
//
    public static void showInterstitial() {
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "TigerMedia");
        onEvent(mContext, "BFAd_TM_Interstitial", map);
        GoldSource.showInterstitial();
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
        }

        @Override
        public void onRewardedVideoAdClosed() {
            //视频关闭时调用
            LogUtils.log("onRewardedVideoAdClosed");
            //这里我们向用户显示一个对话框，如果他得到了奖励
            listener.onRewardedVideoAdClosed();
        }

        @Override
        public void onRewardedVideoAvailabilityChanged(boolean b) {
            LogUtils.log("onRewardedVideoAvailabilityChanged");
        }

        @Override
        public void onRewardedVideoAdStarted() {
            LogUtils.log("onRewardedVideoAdStarted");
        }

        @Override
        public void onRewardedVideoAdEnded() {
            LogUtils.log("onRewardedVideoAdEnded");
        }

        @Override
        public void onRewardedVideoAdRewarded(Placement placement) {
            //当视频得到奖励并且可以给用户奖励时调用
            LogUtils.log("onRewardedVideoAdRewarded" + " " + placement);
            listener.onRewardedVideoAdRewarded(new ISPlacement(placement));
        }

        @Override
        public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
            LogUtils.log("onRewardedVideoAdShowFailed(" + ironSourceError.getErrorCode() + "：" + ironSourceError.getErrorMessage() + ")");
        }

        @Override
        public void onRewardedVideoAdClicked(Placement placement) {
            LogUtils.log("onRewardedVideoAdClicked(" + placement.getPlacementId() + ")");
        }
    };

}
