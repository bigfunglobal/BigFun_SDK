package com.bigfun.sdk.NetWork;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

public interface InterstListener {
    /**
     * 广告开始播放
     * @param ad
     */
    void onInterstitialDisplayed(Ad ad);

    /**
     * 广告页消失
     * @param ad
     */
    void onInterstitialDismissed(Ad ad);

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

    /**
     * 日志回调
     * @param ad
     */
    void onLoggingImpression(Ad ad);
}
