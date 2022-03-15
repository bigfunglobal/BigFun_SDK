package com.bigfun.sdk.listener;


import androidx.annotation.Keep;

import com.bigfun.sdk.model.BFLoginModel;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


import org.json.JSONObject;


public interface LoginListener {

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
     *
     * @param loginResult
     */

    void onComplete(BFLoginModel loginResult);
}
