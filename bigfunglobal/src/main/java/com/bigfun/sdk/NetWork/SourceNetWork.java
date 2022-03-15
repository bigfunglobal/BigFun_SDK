//package com.bigfun.sdk.NetWork;
//
//import static com.bigfun.sdk.BigFunSDK.TAG;
//import static com.bigfun.sdk.BigFunSDK.mActivity;
//import static com.bigfun.sdk.BigFunSDK.mContext;
//import static com.bigfun.sdk.BigFunSDK.onEvent;
//
//import android.app.Activity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//
//import com.bigfun.sdk.BigFunSDK;
//import com.bigfun.sdk.LogUtils;
//import com.bigfun.sdk.model.BigFunViewModel;
//import com.bigfun.sdk.model.ISPlacement;
//import com.bigfun.sdk.type.AdBFSize;
//import com.bigfun.sdk.utils.FunctionUtils;
//import com.ironsource.mediationsdk.ISBannerSize;
//import com.ironsource.mediationsdk.IronSource;
//import com.ironsource.mediationsdk.IronSourceBannerLayout;
//import com.ironsource.mediationsdk.impressionData.ImpressionData;
//import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
//import com.ironsource.mediationsdk.integration.IntegrationHelper;
//import com.ironsource.mediationsdk.logger.IronSourceError;
//import com.ironsource.mediationsdk.model.Placement;
//import com.ironsource.mediationsdk.sdk.BannerListener;
//import com.ironsource.mediationsdk.sdk.InterstitialListener;
//import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class SourceNetWork{
//    private static FrameLayout mBannerParentLayout;
//    private static IronSourceBannerLayout mIronSourceBannerLayout;
//    private static BFRewardedVideoListener listener;
//
//    public SourceNetWork() {
//    }
//
//    private static SourceNetWork instance;
//
//    public static SourceNetWork getInstance() {
//
//        if (instance == null) {
//            synchronized (SourceNetWork.class) {
//                if (instance == null) {
//                    instance = new SourceNetWork();
//                }
//            }
//        }
//        return instance;
//    }
//
//    static Timer timer;
//    public void TimerIronSource() {
//        LogUtils.log("instance:  "+instance+"");
//        timer = new Timer();
//        if(FunctionUtils.getCurrentTask(mContext)) {
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    LogUtils.log("timer: " + "1000");
//                    LogUtils.log("mActivity: " + mActivity + "Ket");
//                    if (mActivity != null && !TextUtils.isEmpty(BigFunViewModel.SourceAppKey)) {
//                        LogUtils.log("mActivity123123: " + mActivity + "Ket" + BigFunViewModel.SourceAppKey);
//                        timer.cancel();
//                        timer = null;
//                        initIronSource();
//                    }
//                }
//            }, 0, 1000);
//        }
//    }
//
//    public static void initListener(){
//        //确保为正在启动的每个产品设置一个侦听器
//        //设置视频侦听器
//        IronSource.setRewardedVideoListener(rewardedVideoListener);
//        //设置插屏侦听器
//        IronSource.setInterstitialListener(interstitialListener);
//        //添加Impression数据侦听器
//        IronSource.addImpressionDataListener(impressionDataListener);
//        IronSource.isRewardedVideoAvailable();
//    }
//
//    private void initIronSource() {
//        IntegrationHelper.validateIntegration(mActivity);
//            //设置IronSource用户id
//            IronSource.setUserId(IronSource.getAdvertiserId(mContext));
//             //初始化IronSource SDK
//            IronSource.getAdvertiserId(mContext);
//            IronSource.init(mActivity, BigFunViewModel.SourceAppKey);
////        网络连接状态
//            IronSource.shouldTrackNetworkState(mContext, true);
//            IronSource.isRewardedVideoAvailable();
//            IronSource.loadInterstitial();
//    }
//
//    public static void showRewardedVideo(BFRewardedVideoListener bflistener) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("adBFPlatForm", "IronSource");
//        onEvent(mContext, "BFAd_IS_RewardedVideo", map);
//        listener = bflistener;
//        if (IronSource.isRewardedVideoAvailable())
//            //show rewarded video
//            IronSource.showRewardedVideo();
//    }
//    public static void showRewardedVideo() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("adBFPlatForm", "IronSource");
//        onEvent(mContext, "BFAd_IS_RewardedVideo", map);
//        if (IronSource.isRewardedVideoAvailable())
//            //show rewarded video
//            IronSource.showRewardedVideo();
//    }
//
//    public static void showInterstitial() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("adBFPlatForm", "IronSource");
//        onEvent(mContext, "BFAd_IS_Interstitial", map);
//        if (IronSource.isInterstitialReady()) {
////                    //show the interstitial
//            IronSource.showInterstitial();
//        }
//    }
//
//    public static void onDestroy() {
//        if(timer!=null){
//            timer.cancel();
//        }
//        if (mIronSourceBannerLayout != null) {
//            IronSource.destroyBanner(mIronSourceBannerLayout);
//            if (mBannerParentLayout != null) {
//                mBannerParentLayout.removeView(mIronSourceBannerLayout);
//            }
//        }
//    }
//
//    /**
//     * 创建并加载IronSource横幅
//     */
//    private static ISBannerSize isBannerSize = ISBannerSize.BANNER;
//
//    public static void createAndloadBanner(FrameLayout mbannerParentLayout, AdBFSize size) {
//
//        if (size.equals(AdBFSize.BANNER_HEIGHT_50))
//            isBannerSize = ISBannerSize.BANNER;
//        if (size.equals(AdBFSize.BANNER_HEIGHT_90))
//            isBannerSize = ISBannerSize.LARGE;
//        if (size.equals(AdBFSize.RECTANGLE_HEIGHT_250))
//            isBannerSize = ISBannerSize.RECTANGLE;
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("adBFPlatForm", "IronSource");
//        map.put("adSize", isBannerSize);
//        onEvent(mContext, "BFAd_IS_Banner", map);
//        mBannerParentLayout = mbannerParentLayout;
//
//        //使用IronSource实例化IronSourceBanner对象。createBanner API
//        mIronSourceBannerLayout = IronSource.createBanner(mActivity, isBannerSize);
//
//        //将IronSourceBanner添加到容器中
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT);
//        mBannerParentLayout.addView(mIronSourceBannerLayout, 0, layoutParams);
//
//        if (mIronSourceBannerLayout != null) {
//            //设置横幅侦听器
//            mIronSourceBannerLayout.setBannerListener(new BannerListener() {
//                @Override
//                public void onBannerAdLoaded() {
//                     LogUtils.log( "onBannerAdLoaded");
//                    //由于默认情况下横幅容器“消失”，我们需要在横幅准备就绪后立即使其可见
//                    mBannerParentLayout.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onBannerAdLoadFailed(IronSourceError error) {
//                     LogUtils.log( "onBannerAdLoadFailed" + "IronSourceError： " + error);
//                }
//
//                @Override
//                public void onBannerAdClicked() {
//                     LogUtils.log( "onBannerAdClicked");
//                }
//
//                @Override
//                public void onBannerAdScreenPresented() {
//                     LogUtils.log( "onBannerAdScreenPresented");
//                }
//
//                @Override
//                public void onBannerAdScreenDismissed() {
//                     LogUtils.log( "onBannerAdScreenDismissed");
//                }
//
//                @Override
//                public void onBannerAdLeftApplication() {
//                     LogUtils.log( "onBannerAdLeftApplication");
//                }
//            });
//            //将广告加载到创建的横幅中
//            IronSource.loadBanner(mIronSourceBannerLayout);
//        } else {
//            Toast.makeText(mActivity, "IronSource.createBanner returned null", Toast.LENGTH_LONG).show();
//        }
//    }
//    // --------- IronSource视频监听器 ---------
//    static RewardedVideoListener rewardedVideoListener=new RewardedVideoListener() {
//        @Override
//        public void onRewardedVideoAdOpened() {
//            //打开视频时调用
//            LogUtils.log( "onRewardedVideoAdOpened");
//        }
//
//        @Override
//        public void onRewardedVideoAdClosed() {
//            //视频关闭时调用
//            LogUtils.log( "onRewardedVideoAdClosed");
//            //这里我们向用户显示一个对话框，如果他得到了奖励
//            listener.onRewardedVideoAdClosed();
//        }
//
//        @Override
//        public void onRewardedVideoAvailabilityChanged(boolean b) {
//            //视频可用性更改时调用
//            LogUtils.log( "onRewardedVideoAvailabilityChanged" + " " + b);
//
//        }
//
//        @Override
//        public void onRewardedVideoAdStarted() {
//            //视频开始时调用
//            LogUtils.log( "onRewardedVideoAdStarted");
//        }
//
//        @Override
//        public void onRewardedVideoAdEnded() {
//            //视频结束时调用
//            LogUtils.log( "onRewardedVideoAdEnded");
//        }
//
//        @Override
//        public void onRewardedVideoAdRewarded(Placement placement) {
//            //当视频得到奖励并且可以给用户奖励时调用
//            LogUtils.log( "onRewardedVideoAdRewarded" + " " + placement);
//            listener.onRewardedVideoAdRewarded(new ISPlacement(placement));
//        }
//
//        @Override
//        public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
//            //视频无法显示时调用
//            //您可以通过访问IronSourceError对象来获取错误数据
//            // IronSourceError.getErrorCode();
//            // IronSourceError.getErrorMessage();
//            LogUtils.log( "onRewardedVideoAdShowFailed" + "ironSourceError： " + ironSourceError);
//        }
//
//        @Override
//        public void onRewardedVideoAdClicked(Placement placement) {
//
//        }
//    };
//
//
//    // --------- IronSource 插屏广告 Listener ---------
//
//    static InterstitialListener interstitialListener=new InterstitialListener() {
//        @Override
//        public void onInterstitialAdClicked() {
//            //单击间隙时调用
//            LogUtils.log( "onInterstitialAdClicked");
//        }
//
//        @Override
//        public void onInterstitialAdReady() {
//            //当间隙准备好时调用
//
//            LogUtils.log( "onInterstitialAdReady");
//        }
//
//        @Override
//        public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
//            //当间隙加载失败时调用
////您可以通过访问IronSourceError对象来获取错误数据
////         IronSourceError.getErrorCode();
////         IronSourceError.getErrorMessage();
//            LogUtils.log( "onInterstitialAdLoadFailed" + "ironSourceError： " + ironSourceError);
//
//        }
//
//        @Override
//        public void onInterstitialAdOpened() {
//            //显示间隙时调用
//            LogUtils.log( "onInterstitialAdOpened");
//        }
//
//        @Override
//        public void onInterstitialAdClosed() {
//            //当间隙闭合时调用
//            LogUtils.log( "onInterstitialAdClosed");
//
//        }
//
//        @Override
//        public void onInterstitialAdShowSucceeded() {
//            //成功显示间隙时调用
//            LogUtils.log( "onInterstitialAdShowSucceeded");
//        }
//
//        @Override
//        public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
//            //当间隙未显示时调用
//
////您可以通过访问IronSourceError对象来获取错误数据
//            // IronSourceError.getErrorCode();
//            // IronSourceError.getErrorMessage();
//            LogUtils.log( "onInterstitialAdShowFailed" + "ironSourceError： " + ironSourceError);
//
//        }
//    };
//
//    //------------------------
//    static ImpressionDataListener impressionDataListener=new ImpressionDataListener() {
//        @Override
//        public void onImpressionSuccess(ImpressionData impressionData) {
//            if (impressionData != null) {
//                LogUtils.log( "onImpressionSuccess " + impressionData);
//            }
//        }
//    };
//}
