package com.bigfun.sdk.login;

import androidx.annotation.Keep;

@Keep
public interface Callback<T> {
    @Keep
    void onResult(T result);

    @Keep
    void onFail(String msg);
}
