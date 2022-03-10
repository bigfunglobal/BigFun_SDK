package com.bigfun.sdk.login;


import androidx.annotation.Keep;

import com.bigfun.sdk.model.BFLoginModel;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


import org.json.JSONObject;

@Keep
public interface LoginListener {

    /**
     * 取消
     */
    @Keep
    void onCancel();

    /**
     * 错误
     */
    @Keep
    void onError(String error);

    /**
     * 完成
     *
     * @param loginResult
     */
    @Keep
    void onComplete(BFLoginModel loginResult);
}
