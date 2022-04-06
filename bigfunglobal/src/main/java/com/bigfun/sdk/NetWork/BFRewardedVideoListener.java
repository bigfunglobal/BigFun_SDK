package com.bigfun.sdk.NetWork;

import com.bigfun.sdk.model.TMISPlacement;

public interface BFRewardedVideoListener {

    void onRewardedVideoAdClosed();

    void onRewardedVideoAdRewarded(TMISPlacement placement);

}
