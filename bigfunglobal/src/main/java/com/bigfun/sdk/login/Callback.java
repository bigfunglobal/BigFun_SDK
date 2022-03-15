package com.bigfun.sdk.login;

import androidx.annotation.Keep;

@Keep
public interface Callback<T> {

    void onResult(T result);


    void onFail(String msg);
}
