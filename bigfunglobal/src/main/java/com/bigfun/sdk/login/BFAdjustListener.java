package com.bigfun.sdk.login;

import androidx.annotation.Keep;

import com.adjust.sdk.AdjustAttribution;

@Keep
public interface BFAdjustListener {
    void onAttributionChanged(AdjustAttribution attribution);
}
