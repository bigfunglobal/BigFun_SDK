package com.bigfunglobal.bigfun;

import static androidx.core.app.ActivityCompat.startIntentSenderForResult;
import static com.bigfun.sdk.AdvertisingIdClient.getAdId;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.NetWork.AdViewListener;
import com.bigfun.sdk.NetWork.InterstListener;
import com.bigfun.sdk.NetWork.RewardVideoListener;
import com.bigfun.sdk.ResponseListener;
import com.bigfun.sdk.google.GooglePayUpdatedListener;
import com.bigfun.sdk.login.LoginListener;
import com.bigfun.sdk.login.ShareListener;
import com.bigfun.sdk.type.AdBFPlatForm;
import com.bigfun.sdk.type.AdBFSize;
import com.bigfun.sdk.type.ShareContentType;


import com.facebook.FacebookException;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private String text = "";
    private LoginButton loginButton;
    private Button button, share, btn_phone_login, btn_init, btn_verification, out, AdView, Inter,google;
    private String TAG = "MainActivity";
    private ShareDialog shareDialog;
    private static final int FILE_SELECT_CODE = 100;
    private static final int REQUEST_SHARE_FILE_CODE = 120;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 121;
    private BillingClient mBillingClient;
    private TextView tvShareFileUri;
    private Uri shareFileUrl = null;
    private String getAdId;
    private EditText et_phone, et_Verification;
//    private InterstitialAd.InterstitialAdLoadConfigBuilder loadConfigBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        share = findViewById(R.id.share);
        btn_init = findViewById(R.id.btn_init);
        AdView = findViewById(R.id.AdView);
        Inter = findViewById(R.id.Inter);
        google = findViewById(R.id.google);
        out = findViewById(R.id.out);
        et_Verification = findViewById(R.id.et_Verification);
        btn_verification = findViewById(R.id.btn_verification);
        et_phone = findViewById(R.id.et_phone);
        btn_phone_login = findViewById(R.id.btn_phone_login);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAdId=getAdId(MainActivity.this);
                    Log.e("getAdid",getAdId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse("https://app.adjust.com/4emzrrm?campaign=TopGameSDK&redirect=https%3A%2F%2Fcdna.wolfmedia.in%2Ftopgame%2FRummyking%2FTeenPtti-AB.apk");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                Map<String, Object> map = new HashMap<>();
//                map.put("mobile", et_phone.getText().toString());
//                map.put("mobile", "919415608690");
//                BigFunSDK.getInstance().sendSms(map, new ResponseListener() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(MainActivity.this, "验证码发送成功",
//                                Toast.LENGTH_LONG).show();
//                        Log.e("onSuccess", "短信发送成功");
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        Log.e("onFail", msg);
//                    }
//                });
            }
        });

        btn_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BigFunSDK.getInstance().Statistik("","","");
//                Map<String, Object> map = new HashMap<>();
//                map.put("mobile", et_phone.getText().toString());
//                map.put("code", et_Verification.getText().toString());
//                BigFunSDK.getInstance().loginWithCode(map, new ResponseListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.e("onSuccess", "登陆成功！");
//                        Toast.makeText(MainActivity.this, "验证码正确",
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        Log.e("onFail", msg);
//                    }
//                });
            }
        });


        // Find the Ad Container
        AdSettings.setTestMode(true);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
//        AdSettings.addTestDevice(uniqueID);
        AdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigFunSDK.getInstance().AdViewLoadAd(MainActivity.this, AdBFPlatForm.Facebook,"YOUR_PLACEMENT_ID", adContainer, AdBFSize.BANNER_HEIGHT_50, new AdViewListener() {

                    @Override
                    public void onAdError(AdError adError) {
                        Log.e("adadad",adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });
            }
        });
        Inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigFunSDK.getInstance().InterstitialAdLoadAd(MainActivity.this,AdBFPlatForm.Facebook,"1998327616988725_2104448113043341", new InterstListener() {
                    /**
                     * 广告开始播放
                     * @param ad
                     */
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }
                    /**
                     * 广告页消失
                     * @param ad
                     */
                    @Override
                    public void onInterstitialDismissed(Ad ad) {

                    }
                    /**
                     * 错误
                     * @param adError
                     */
                    @Override
                    public void onAdError(AdError adError) {
                        Log.e("onError",adError.getErrorMessage());
                    }
                    /**
                     * 加载完成回调
                     * @param ad
                     */
                    @Override
                    public void onAdLoaded(Ad ad) {

                    }
                    /**
                     * 点击回调
                     * @param ad
                     */
                    @Override
                    public void onAdClicked(Ad ad) {

                    }
                    /**
                     * 日志回调
                     * @param ad
                     */
                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });
            }
        });


        try {
            int i = 0;
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                i++;
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String KeyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                //KeyHash 就是你要的，不用改任何代码  复制粘贴 ;
                Log.e("获取应用KeyHash", "KeyHash: " + KeyHash);
            }
        } catch (Exception e) {

        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> permissionList = new ArrayList<>();
                permissionList.add("public_profile");
                permissionList.add("email");
                BigFunSDK.getInstance().BigFunFBLogin(MainActivity.this, permissionList, new LoginListener() {
                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onCancel: facebook登录取消");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e(TAG, "onError: " + error.getMessage());
                    }

                    @Override
                    public void onComplete(LoginResult loginResult) {
                        Log.e(TAG, "onSuccess: " + loginResult.toString());
                        Toast.makeText(MainActivity.this, "Facebook登录成功",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Uri uri = null;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 * @param activity 必填
                 * @param contentType 分享数据类型 必填
                 * @param shareFileUri 设置分享文件路径
                 * @param textContent 设置文本内容
                 * @param title 设置标题
                 * @param requestCode 置为ActivityResult requestCode，默认值为-1
                 */
                BigFunSDK.getInstance().BigFunShare(MainActivity.this, ShareContentType.TEXT, uri, "11111", "22222", 100);
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                // 比如发送文本形式的数据内容
//// 指定发送的内容
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//// 指定发送内容的类型
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, "share to"));
//                if (ShareDialog.canShow(ShareLinkContent.class)) {
//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
////                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                        .build();
//                BigFunSDK.getInstance().BigFunFBShare(MainActivity.this,linkContent,new ShareListener() {
//                    @Override
//                    public void onCancel() {
//                        Log.e(TAG, "onCancel: facebook取消");
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Log.e(TAG, "onError: " + error.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete(Sharer.Result result) {
//                        Log.e(TAG, "onSuccess: " + result.toString());
//                        Toast.makeText(MainActivity.this, "Facebook分享成功",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });
//        out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BigFunSDK.getInstance().Logout();
//                BigFunSDK.getInstance().RewardedVideoLoadAd(MainActivity.this,AdBFPlatForm.Facebook,"YOUR_PLACEMENT_ID", new RewardVideoListener() {
//                    /**
//                     * 错误
//                     * @param adError
//                     */
//                    @Override
//                    public void onAdError(AdError adError) {
//
//                    }
//                    /**
//                     * 加载完成回调
//                     * @param ad
//                     */
//                    @Override
//                    public void onAdLoaded(Ad ad) {
//
//                    }
//                    /**
//                     * 点击回调
//                     * @param ad
//                     */
//                    @Override
//                    public void onAdClicked(Ad ad) {
//
//                    }
//                    /**
//                     * 日志回调
//                     * @param ad
//                     */
//                    @Override
//                    public void onLoggingImpression(Ad ad) {
//
//                    }
//                    /**
//                     *视频一直播放到最后。
//                     * 您可以使用此事件初始化奖励
//                     */
//                    @Override
//                    public void onRewardedVideoCompleted() {
//
//                    }
//                    /**
//                     * 奖励视频广告已关闭
//                     * 通过关闭应用程序，或关闭终端车
//                     */
//                    @Override
//                    public void onRewardedVideoClosed() {
//
//                    }
//                });
//            }
//        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 */
//                BigFunSDK.getInstance().GooglePay(MainActivity.this, "premium_upgrade", new GooglePayUpdatedListener() {
//                    @Override
//                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
//                        Log.e("adad","1313131");
//                    }
//                });
                BigFunSDK.getInstance().BigFunGoogleLogin(MainActivity.this,"539033930849-l4gkqgs24fm72fh2hu5u9bkn2csajn6l.apps.googleusercontent.com");
            }
        });

    }


    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // 在这里处理从 Billing 库更新购买的回，对 purchases 进行处理
        }
    };

    /**
     * 广告释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BigFunSDK.getInstance().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BigFunSDK.getInstance().onActivityResult(requestCode, resultCode, data);
        Log.e("DemoActivity", "requestCode=" + requestCode + " resultCode=" + resultCode);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            shareFileUrl = data.getData();
            tvShareFileUri.setText(shareFileUrl.toString());

        } else if (requestCode == REQUEST_SHARE_FILE_CODE) {
            // todo share complete.
        }else if(requestCode == BigFunSDK.SIGN_LOGIN) {
                try {
                    SignInCredential credential = BigFunSDK.getInstance().BigFunIdentity(this).getSignInCredentialFromIntent(data);
                    Log.e("credential",credential.getId());
                } catch (ApiException e) {
                    // The ApiException status code indicates the detailed failure reason.
                    Log.e("asdadasd",e.getMessage());
            }
        }
    }

}