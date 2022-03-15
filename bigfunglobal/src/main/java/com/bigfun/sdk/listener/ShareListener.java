package com.bigfun.sdk.listener;

import androidx.annotation.Keep;

import com.bigfun.sdk.model.BFShareModel;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;

public interface ShareListener {
    /**
     * 取消
     */

    void onCancel();

    /**
     * 错误
     */

    void onError(String error);

    /**
     * 完成
     * @param result
     */

    void onComplete(BFShareModel result);
}
