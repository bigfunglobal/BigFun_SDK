//package com.bigfun.sdk.NetWork;
//
//import static com.bigfun.sdk.BigFunSDK.TAG;
//
//import android.util.Log;
//
//import com.bigfun.sdk.LogUtils;
//import com.ironsource.mediationsdk.logger.IronSourceError;
//import com.ironsource.mediationsdk.model.Placement;
//import com.ironsource.mediationsdk.sdk.InterstitialListener;
//import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
//
//
//public class TMNetWork implements InterstitialListener, RewardedVideoListener {
//
//    public TMNetWork() {
//
//    }
//
//    private static TMNetWork instance;
//
//    public static TMNetWork getInstance() {
//
//        if (instance == null) {
//            synchronized (TMNetWork.class) {
//                if (instance == null) {
//                    instance = new TMNetWork();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void init(){
////        GoldSource.setInterstitialListener(this);
////        GoldSource.setRewardedVideoListener(this);
//    }
//
//
//    //----------插屏-----------
//    @Override
//    public void onInterstitialAdReady() {
//         LogUtils.log( "onInterstitialAdReady");
//    }
//
//    @Override
//    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
//         LogUtils.log( "onInterstitialAdLoadFailed("+ironSourceError.getErrorCode()+"："+ironSourceError.getErrorMessage()+")");
//    }
//
//    @Override
//    public void onInterstitialAdOpened() {
//         LogUtils.log( "onInterstitialAdOpened");
//    }
//
//    @Override
//    public void onInterstitialAdClosed() {
//         LogUtils.log( "onInterstitialAdClosed");
//    }
//
//    @Override
//    public void onInterstitialAdShowSucceeded() {
//         LogUtils.log( "onInterstitialAdShowSucceeded");
//    }
//
//    @Override
//    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
//         LogUtils.log( "onInterstitialAdShowFailed("+ironSourceError.getErrorCode()+"："+ironSourceError.getErrorMessage()+")");
//    }
//
//    @Override
//    public void onInterstitialAdClicked() {
//         LogUtils.log( "onInterstitialAdClicked");
//    }
//
//
//    //--------奖励视屏-------------
//
//    @Override
//    public void onRewardedVideoAdOpened() {
//         LogUtils.log( "onRewardedVideoAdOpened");
//    }
//
//    @Override
//    public void onRewardedVideoAdClosed() {
//         LogUtils.log( "onRewardedVideoAdClosed");
//    }
//
//    @Override
//    public void onRewardedVideoAvailabilityChanged(boolean b) {
//         LogUtils.log( "onRewardedVideoAvailabilityChanged");
//    }
//
//    @Override
//    public void onRewardedVideoAdStarted() {
//         LogUtils.log( "onRewardedVideoAdStarted");
//    }
//
//    @Override
//    public void onRewardedVideoAdEnded() {
//         LogUtils.log( "onRewardedVideoAdEnded");
//    }
//
//    @Override
//    public void onRewardedVideoAdRewarded(Placement placement) {
//         LogUtils.log( "onRewardedVideoAdRewarded");
//    }
//
//    @Override
//    public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
//         LogUtils.log( "onRewardedVideoAdShowFailed("+ironSourceError.getErrorCode()+"："+ironSourceError.getErrorMessage()+")");
//    }
//
//    @Override
//    public void onRewardedVideoAdClicked(Placement placement) {
//         LogUtils.log( "onRewardedVideoAdClicked(" + placement.getPlacementId() + ")");
//    }
//}
