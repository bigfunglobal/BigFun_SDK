package com.bigfun.sdk.NetWork;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
//import com.ironsource.mediationsdk.model.Placement;

public interface RewardVideoListener {
    /**
     * 错误
     * @param adError
     */
    void onAdError(AdError adError);

    /**
     * 加载完成回调
     * @param ad
     */
    void onAdLoaded(Ad ad);

    /**
     * 点击回调
     * @param ad
     */
    void onAdClicked(Ad ad);
//    void onAdClicked(Ad ad, Placement placement);

    /**
     * 日志回调
     * @param ad
     */
    void onLoggingImpression(Ad ad);

    /**
     *视频一直播放到最后。
     * 您可以使用此事件初始化奖励
     */
//    void onRewardedVideoCompleted(Placement placement);
    void onRewardedVideoCompleted();

    /**
     * 奖励视频广告已关闭
     * 通过关闭应用程序，或关闭终端车
     */
    void onRewardedVideoClosed();
}
