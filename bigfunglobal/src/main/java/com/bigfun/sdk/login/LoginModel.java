package com.bigfun.sdk.login;

import static com.bigfun.sdk.BigFunSDK.mActivity;
import static com.bigfun.sdk.BigFunSDK.mContext;
import static com.bigfun.sdk.BigFunSDK.onEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bigfun.sdk.LogUtils;
import com.bigfun.sdk.listener.LoginListener;
import com.bigfun.sdk.listener.ShareListener;
import com.bigfun.sdk.model.BFLoginModel;
import com.bigfun.sdk.model.BFShareModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareDialog;
//import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
//import com.google.android.gms.auth.api.identity.Identity;
//import com.google.android.gms.auth.api.identity.SignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginModel {
    private static CallbackManager callbackManager;
    public LoginModel() {
        callbackManager = CallbackManager.Factory.create();
    }

    private static LoginModel instance;
//    private static SignInClient signInClient;
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

    public static final void facebookLogin(Context activity, List<String> permissionList, final LoginListener listener) {

        LoginManager.getInstance().logInWithReadPermissions((Activity) activity, permissionList);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                listener.onComplete(new BFLoginModel(loginResult));
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                listener.onError(e.getMessage());
            }
        });
    }

    public static final void facebookShare(Context context, ShareContent linkContent, final ShareListener listener) {

        ShareDialog shareDialog = new ShareDialog((Activity) context);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                listener.onComplete(new BFShareModel(result));
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                listener.onError(e.getMessage());
            }
        });
        shareDialog.show(linkContent);
    }
//    public static void Login(Activity activity, GetSignInIntentRequest mGetSignInIntentRequest){
//        signInClient=Identity.getSignInClient(mContext);
//        signInClient
//                .getSignInIntent(mGetSignInIntentRequest)
//                .addOnSuccessListener(
//                        result -> {
//                            try {
//                                activity.startIntentSenderForResult(
//                                        result.getIntentSender(),
//                                        SIGN_LOGIN,
//                                        /* fillInIntent= */ null,
//                                        /* flagsMask= */ 0,
//                                        /* flagsValue= */ 0,
//                                        /* extraFlags= */ 0,
//                                        /* options= */ null);
//                            } catch (IntentSender.SendIntentException e) {
//                                Log.e("BigFunSDK", "Google Sign-in failed");
//                            }
//                        })
//                .addOnFailureListener(
//                        e -> {
//                            Log.e("BigFunSDK", "Google Sign-in failed", e);
//                        });
//    }

    /**
     * 退出Facebook登录
     */
    public static void BigFunLogout() {

        //退出google 登录
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
//        if (account != null) {
//            signOut();
//        }
    }

//        private static void signOut() {
//            signInClient.signOut()
//                    .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // ...
//                            LogUtils.log("Google out"+"11111111");
//                        }
//                    });
//        }

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

    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}
