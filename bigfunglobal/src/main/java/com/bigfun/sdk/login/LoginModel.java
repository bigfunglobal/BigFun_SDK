package com.bigfun.sdk.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

public class LoginModel {
    private CallbackManager callbackManager;
    public LoginModel() {
        callbackManager = CallbackManager.Factory.create();
    }

    private static LoginModel instance;

    public static LoginModel getInstance() {
        if (instance == null) {
            synchronized (LoginModel.class) {
                if (instance == null) {
                    instance = new LoginModel();
                }
            }
        }
        return instance;
    }

    public final void facebookLogin(Context activity,List<String> permissionList,final LoginListener listener) {

        LoginManager.getInstance().logInWithReadPermissions((Activity) activity, permissionList);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Intrinsics.checkNotNullParameter(loginResult, "loginResult");
//                LoginModel loginModel = LoginModel.this;
//                AccessToken accessToken = loginResult.getAccessToken();
//                Intrinsics.checkNotNullExpressionValue(accessToken, "loginResult.accessToken");
//                loginModel.getFacebookInfo(accessToken, listener);
                listener.onComplete(loginResult);
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                listener.onError(e);
            }
        });
    }

    public final void facebookShare(Context context,ShareLinkContent linkContent ,final ShareListener listener) {
        ShareDialog shareDialog = new ShareDialog((Activity) context);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                listener.onComplete(result);
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                listener.onError(e);
            }
        });
        shareDialog.show(linkContent);
    }

//    private final void getFacebookInfo(AccessToken accessToken, final IFBLoginListener listener) {
//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
//                if (jsonObject != null) {
//                    listener.onComplete(jsonObject.toString());
//                } else {
//                    listener.onError(new FacebookException("login fail"));
//                }
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}
