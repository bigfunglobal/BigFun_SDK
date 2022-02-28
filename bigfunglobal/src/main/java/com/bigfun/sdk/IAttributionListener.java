package com.bigfun.sdk;

import androidx.annotation.Keep;

@Keep
public interface IAttributionListener {
    void attribution(String channelCode, String source);
}
