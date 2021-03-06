package com.bigfun.sdk.listener;

import androidx.annotation.Keep;

@Keep
public interface ResponseListener {
    /**
     * 成功
     */
    void onSuccess(String data);

    /**
     * 错误信息
     * @param msg
     */
    void onFail(String msg);
}
