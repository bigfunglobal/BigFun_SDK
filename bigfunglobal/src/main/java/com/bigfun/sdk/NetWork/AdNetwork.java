//package com.bigfun.sdk.NetWork;
//
//import static com.bigfun.sdk.BigFunSDK.TAG;
//import static com.bigfun.sdk.BigFunSDK.mContext;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//import com.bigfun.sdk.BigFunSDK;
//import com.bigfun.sdk.LogUtils;
//import com.bigfun.sdk.model.BigFunViewModel;
//import com.bigfun.sdk.type.AdBFSize;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.AdListener;
//import com.facebook.ads.AdSize;
//import com.facebook.ads.AdView;
//import com.facebook.ads.InterstitialAd;
//import com.facebook.ads.InterstitialAdListener;
//import com.facebook.ads.RewardedVideoAd;
//import com.facebook.ads.RewardedVideoAdListener;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class AdNetwork {
//    private AdView adView;
//    private InterstitialAd interstitialAd;
//    private RewardedVideoAd rewardedVideoAd;
//    public AdNetwork(){
//
//    }
//    private static AdNetwork instance;
//
//    public static AdNetwork getInstance() {
//        if (instance == null) {
//            synchronized (AdNetwork.class) {
//                if (instance == null) {
//                    instance = new AdNetwork();
//                }
//            }
//        }
//        return instance;
//    }
//    private AdSize adSize=AdSize.BANNER_HEIGHT_50;
//    public void adViewLoadAd(Context context, String placementId, FrameLayout adContainer, AdBFSize size){
//        Map<String, Object> map = new HashMap<>();
//        map.put("placementId", BigFunViewModel.bannerAdId);
//        map.put("adBFPlatForm", "Facebook");
//        map.put("adSize", adSize);
//        BigFunSDK.onEvent(mContext, "BFAd_FB_Banner", map);
//        if(size.equals(AdBFSize.BANNER_HEIGHT_50))
//            adSize= AdSize.BANNER_HEIGHT_50;
//        if(size.equals(AdBFSize.BANNER_HEIGHT_90))
//            adSize=AdSize.BANNER_HEIGHT_90;
//        if(size.equals(AdBFSize.RECTANGLE_HEIGHT_250))
//            adSize=AdSize.RECTANGLE_HEIGHT_250;
//        adView = new AdView(context, placementId, adSize);
//        // Add the ad view to your activity layout
//        adContainer.addView(adView);
//
//        AdListener adListener = new AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                Log.e("FBBannerError",adError.getErrorMessage());
//            }
//            //加载回调
//            @Override
//            public void onAdLoaded(Ad ad) {
//                 LogUtils.log( "onAdLoaded");
//// Ad loaded callback
//            }
//            //点击回调
//            @Override
//            public void onAdClicked(Ad ad) {
//                 LogUtils.log( "onAdClicked");
//// Ad clicked callback
//            }
//            //日志回调
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                 LogUtils.log( "onLoggingImpression");
//// Ad impression logged callback
//            }
//        };
//
//// Request an ad
//        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
//
//    }
//
//    public void rewardedVideoLoadAd(Context context,String placementId,BFRewardedVideoListener listener){
//        Map<String, Object> map = new HashMap<>();
//        map.put("placementId", BigFunViewModel.rewardedVideoId);
//        map.put("adBFPlatForm", "Facebook");
//        BigFunSDK.onEvent(mContext, "BFAd_FB_RewardedVideo", map);
//        rewardedVideoAd=new RewardedVideoAd(context, placementId);
//        RewardedVideoAdListener rewardedVideoAdListener=new RewardedVideoAdListener() {
//            /**
//             *视频一直播放到最后。
//             * 您可以使用此事件初始化奖励
//             */
//            @Override
//            public void onRewardedVideoCompleted() {
//                 LogUtils.log( "onRewardedVideoCompleted");
//                listener.onRewardedVideoAdClosed();
//            }
//            /**
//             * 奖励视频广告已关闭
//             * 通过关闭应用程序，或关闭终端车
//             */
//            @Override
//            public void onRewardedVideoClosed() {
//                 LogUtils.log( "onRewardedVideoClosed");
//            }
//            /**
//             * 错误
//             * @param adError
//             */
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                Log.e("FBRewardedVideoError",adError.getErrorMessage());
//            }
//            /**
//             * 加载完成回调
//             * @param ad
//             */
//            @Override
//            public void onAdLoaded(Ad ad) {
//                 LogUtils.log( "onAdLoaded");
//                rewardedVideoAd.show();
//            }
//            /**
//             * 点击回调
//             * @param ad
//             */
//            @Override
//            public void onAdClicked(Ad ad) {
//                 LogUtils.log( "onAdClicked");
//            }
//            /**
//             * 日志回调
//             * @param ad
//             */
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                 LogUtils.log( "onLoggingImpression");
//            }
//        };
//
//
//        rewardedVideoAd.loadAd(
//                rewardedVideoAd.buildLoadAdConfig()
//                        .withAdListener(rewardedVideoAdListener)
//                        .build());
//
//    }
//
//
//    public void interstitialAdLoadAd(Context context,String placementId){
//        Map<String, Object> map = new HashMap<>();
//        map.put("placementId", BigFunViewModel.interstitialId);
//        map.put("adBFPlatForm", "Facebook");
//        BigFunSDK.onEvent(mContext, "BFAd_FB_Interstitial", map);
//                interstitialAd = new InterstitialAd(context, placementId);
//        // Create listeners for the Interstitial Ad
//        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
//        //间隙广告
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//                // Interstitial ad displayed callback
//                 LogUtils.log( "onInterstitialDisplayed");
//            }
//            //间隙性回叫
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                // Interstitial dismissed callback
//                 LogUtils.log( "onInterstitialDismissed");
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Ad error callback
//                Log.e("FBInterstitialonError",adError.getErrorMessage());
//            }
//             //加载回调
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Interstitial ad is loaded and ready to be displayed
//                 LogUtils.log( "onAdLoaded");
//                // Show the ad
//                interstitialAd.show();
//
//            }
//           //点击回调
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Ad clicked callback
//                 LogUtils.log( "onAdClicked");
//            }
//            //日志回调
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // Ad impression logged callback
//                 LogUtils.log( "onLoggingImpression");
//            }
//        };
//
//        // For auto play video ads, it's recommended to load the ad
//        // at least 30 seconds before it is shown
//        interstitialAd.loadAd(
//                interstitialAd.buildLoadAdConfig()
//                        .withAdListener(interstitialAdListener)
//                        .build());
//
//
//
//    }
//
//
//
//
//    public void dstroy(){
//        if (adView != null) {
//            adView.destroy();
//        }
//        if (rewardedVideoAd != null) {
//            rewardedVideoAd.destroy();
//            rewardedVideoAd = null;
//        }
//        if (interstitialAd != null) {
//            interstitialAd.destroy();
//            interstitialAd = null;
//        }
//    }
//
//}
