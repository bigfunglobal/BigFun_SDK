package com.bigfun.sdk.NetWork;

import static com.bigfun.sdk.BigFunSDK.TAG;
import static com.bigfun.sdk.BigFunSDK.mContext;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.model.BigFunViewModel;
import com.bigfun.sdk.model.ISPlacement;
import com.bigfun.sdk.type.AdBFSize;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.impressionData.ImpressionData;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

import java.util.HashMap;
import java.util.Map;

public class SourceNetWork implements RewardedVideoListener, InterstitialListener, ImpressionDataListener {
    private FrameLayout mBannerParentLayout;
    private IronSourceBannerLayout mIronSourceBannerLayout;
    private ISRewardedVideoListener listener;
    public SourceNetWork(Activity activity){
        initIronSource();
        IronSource.init(activity, BigFunViewModel.SourceAppKey);
        IntegrationHelper.validateIntegration(activity);
    }
    private static SourceNetWork instance;

    public static SourceNetWork getInstance(Activity activity) {
        if (instance == null) {
            synchronized (SourceNetWork.class) {
                if (instance == null) {
                    instance = new SourceNetWork(activity);
                }
            }
        }
        return instance;
    }
    public void initIronSource() {
        //确保为正在启动的每个产品设置一个侦听器
        //设置视频侦听器
        IronSource.setRewardedVideoListener(this);
        //设置插屏侦听器
        IronSource.setInterstitialListener(this);
        //添加Impression数据侦听器
        IronSource.addImpressionDataListener(this);

        //初始化IronSource SDK

        IronSource.setUserId(IronSource.getAdvertiserId(mContext));
        IronSource.getAdvertiserId(mContext);
//        网络连接状态
        IronSource.shouldTrackNetworkState(mContext, true);

    }

    public void showRewardedVideo(ISRewardedVideoListener listener){
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "IronSource");
        BigFunSDK.getInstance().onEvent(mContext, "BFAd_IS_RewardedVideo", map);
        this.listener=listener;
        if (IronSource.isRewardedVideoAvailable())
            //show rewarded video
            IronSource.showRewardedVideo();
    }
    public void showInterstitial(){
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "IronSource");
        BigFunSDK.getInstance().onEvent(mContext, "BFAd_IS_Interstitial", map);
        IronSource.loadInterstitial();

    }
    public void onDestroy() {
        if(mIronSourceBannerLayout!=null) {
            IronSource.destroyBanner(mIronSourceBannerLayout);
            if (mBannerParentLayout != null) {
                mBannerParentLayout.removeView(mIronSourceBannerLayout);
            }
        }
    }
    /**
     * 创建并加载IronSource横幅
     *
     */
    private ISBannerSize isBannerSize=ISBannerSize.BANNER;
    public void createAndloadBanner(Activity activity, FrameLayout mBannerParentLayout, AdBFSize size) {
        Map<String, Object> map = new HashMap<>();
        map.put("adBFPlatForm", "IronSource");
        map.put("adSize", size);
        BigFunSDK.getInstance().onEvent(mContext, "BFAd_IS_Banner", map);
        if(size.equals(AdBFSize.BANNER_HEIGHT_50))
            isBannerSize=ISBannerSize.BANNER;
        if(size.equals(AdBFSize.BANNER_HEIGHT_90))
            isBannerSize=ISBannerSize.LARGE;
        if(size.equals(AdBFSize.RECTANGLE_HEIGHT_250))
            isBannerSize=ISBannerSize.RECTANGLE;
        this.mBannerParentLayout=mBannerParentLayout;

        //使用IronSource实例化IronSourceBanner对象。createBanner API
        mIronSourceBannerLayout = IronSource.createBanner(activity, isBannerSize);

        //将IronSourceBanner添加到容器中
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mBannerParentLayout.addView(mIronSourceBannerLayout, 0, layoutParams);

        if (mIronSourceBannerLayout != null) {
            //设置横幅侦听器
            mIronSourceBannerLayout.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {
                    Log.d(TAG, "onBannerAdLoaded");
                    //由于默认情况下横幅容器“消失”，我们需要在横幅准备就绪后立即使其可见
                    mBannerParentLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                }

                @Override
                public void onBannerAdClicked() {
                    Log.d(TAG, "onBannerAdClicked");
                }

                @Override
                public void onBannerAdScreenPresented() {
                    Log.d(TAG, "onBannerAdScreenPresented");
                }

                @Override
                public void onBannerAdScreenDismissed() {
                    Log.d(TAG, "onBannerAdScreenDismissed");
                }

                @Override
                public void onBannerAdLeftApplication() {
                    Log.d(TAG, "onBannerAdLeftApplication");
                }
            });

            //将广告加载到创建的横幅中
            IronSource.loadBanner(mIronSourceBannerLayout);
        } else {
            Toast.makeText(activity, "IronSource.createBanner returned null", Toast.LENGTH_LONG).show();
        }
    }

    // --------- IronSource视频监听器 ---------

    @Override
    public void onRewardedVideoAdOpened() {
        //打开视频时调用
        Log.d(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //视频关闭时调用
        Log.d(TAG, "onRewardedVideoAdClosed");
        //这里我们向用户显示一个对话框，如果他得到了奖励
        listener.onRewardedVideoAdClosed();
    }

    @Override
    public void onRewardedVideoAvailabilityChanged(boolean b) {
        //视频可用性更改时调用
        Log.d(TAG, "onRewardedVideoAvailabilityChanged" + " " + b);

    }

    @Override
    public void onRewardedVideoAdStarted() {
        //视频开始时调用
        Log.d(TAG, "onRewardedVideoAdStarted");
    }

    @Override
    public void onRewardedVideoAdEnded() {
        //视频结束时调用
        Log.d(TAG, "onRewardedVideoAdEnded");
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {
        //当视频得到奖励并且可以给用户奖励时调用
        Log.d(TAG, "onRewardedVideoAdRewarded" + " " + placement);
        listener.onRewardedVideoAdRewarded(new ISPlacement(placement));
    }

    @Override
    public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
        //视频无法显示时调用
        //您可以通过访问IronSourceError对象来获取错误数据
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onRewardedVideoAdShowFailed" + " " + ironSourceError);
    }

    @Override
    public void onRewardedVideoAdClicked(Placement placement) {

    }

    // --------- IronSource 插屏广告 Listener ---------

    @Override
    public void onInterstitialAdClicked() {
        //单击间隙时调用
        Log.d(TAG, "onInterstitialAdClicked");
    }

    @Override
    public void onInterstitialAdReady() {
        //当间隙准备好时调用
        if (IronSource.isInterstitialReady()) {
//                    //show the interstitial
            IronSource.showInterstitial();
        }
        Log.d(TAG, "onInterstitialAdReady");
    }

    @Override
    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
        //当间隙加载失败时调用
//您可以通过访问IronSourceError对象来获取错误数据
//         IronSourceError.getErrorCode();
//         IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);

    }

    @Override
    public void onInterstitialAdOpened() {
        //显示间隙时调用
        Log.d(TAG, "onInterstitialAdOpened");
    }

    @Override
    public void onInterstitialAdClosed() {
        //当间隙闭合时调用
        Log.d(TAG, "onInterstitialAdClosed");

    }

    @Override
    public void onInterstitialAdShowSucceeded() {
        //成功显示间隙时调用
        Log.d(TAG, "onInterstitialAdShowSucceeded");
    }

    @Override
    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
        //当间隙未显示时调用

//您可以通过访问IronSourceError对象来获取错误数据
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);

    }

    @Override
    public void onImpressionSuccess(ImpressionData impressionData) {
        if (impressionData != null) {
            Log.d(TAG, "onImpressionSuccess " + impressionData);
        }
    }
}
