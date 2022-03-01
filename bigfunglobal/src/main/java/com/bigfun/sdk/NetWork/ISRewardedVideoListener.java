package com.bigfun.sdk.NetWork;

import com.bigfun.sdk.model.ISPlacement;

public interface ISRewardedVideoListener {

    void onRewardedVideoAdClosed();

    void onRewardedVideoAdRewarded(ISPlacement placement);

}
