package com.bigfun.sdk.login;

import androidx.annotation.Keep;

import com.facebook.FacebookException;
import com.facebook.share.Sharer;

public interface ShareListener {
    /**
     * 取消
     */
    @Keep
    void onCancel();

    /**
     * 错误
     */
    @Keep
    void onError(FacebookException error);

    /**
     * 完成
     * @param result
     */
    @Keep
    void onComplete(Sharer.Result result);
}
