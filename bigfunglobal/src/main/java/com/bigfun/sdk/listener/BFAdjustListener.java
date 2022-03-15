package com.bigfun.sdk.listener;

import androidx.annotation.Keep;

import com.adjust.sdk.AdjustAttribution;


public interface BFAdjustListener {
    void onAttributionChanged(AdjustAttribution attribution);
}
