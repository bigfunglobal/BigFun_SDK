package com.bigfunglobal.bigfun;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;

import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.LogUtils;
import com.bigfun.sdk.NetWork.BFRewardedVideoListener;

import com.bigfun.sdk.model.BFLoginModel;
import com.bigfun.sdk.model.ISPlacement;
import com.bigfun.sdk.type.AdBFSize;


import com.facebook.FacebookException;


import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

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
//        integrationHelper用于验证集成。在开始使用之前删除integrationHelper！
//        Log.e("dasdada",BigFunSDK.fhfioafm()+"");

//        if(BigFunSDK.fhfioafm()){
//            Intent intent=new Intent();
//            intent.setClass(MainActivity.this,Activity2.class);
//            startActivity(intent);
//        }else {
//            Intent intent=new Intent();
//            intent.setClass(MainActivity.this,Activity1.class);
//            startActivity(intent);
//        }

        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse("https://app.adjust.com/4emzrrm?campaign=TopGameSDK&redirect=https%3A%2F%2Fcdna.wolfmedia.in%2Ftopgame%2FRummyking%2FTeenPtti-AB.apk");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);

            }
        });

        btn_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BigFunSDK.Statistik("","","");
//                Map<String, Object> map = new HashMap<>();
//                map.put("mobile", et_phone.getText().toString());
//                map.put("code", et_Verification.getText().toString());
//                BigFunSDK.loginWithCode(map, new ResponseListener() {
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
//        AdSettings.setTestMode(true);
//        LinearLayout adContainer = (LinearLayout) findViewById(R.id.bannerContainer);
//        AdSettings.addTestDevice(uniqueID);
        FrameLayout mBannerParentLayout = (FrameLayout) findViewById(R.id.bannerContainer);

        AdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                FrameLayout adContainer=new FrameLayout(MainActivity.this);
//                adContainer.setLayoutParams(new FrameLayout.LayoutParams(100,300));
//                BigFunSDK.ShowBanner(mBannerParentLayout,AdBFSize.BANNER_HEIGHT_50);
            }
        });
        Inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigFunSDK.ShowInterstitialAdLoadAd();
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
                BigFunSDK.BigFunLogout();
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
                BigFunSDK.BigFunShare(MainActivity.this,"11111");
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                // 比如发送文本形式的数据内容
//// 指定发送的内容
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//// 指定发送内容的类型
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, "share to"));
//                if (ShareDialog.canShow(ShareLinkContent.class)) {
//                SharePhoto mSharePhoto=new SharePhoto.Builder()
//                         .setCaption("Test share")
//                            .setBitmap(bitmap)
//                         .build();

//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                        .build();
//                BigFunSDK.BigFunShare(MainActivity.this,linkContent,new ShareListener() {
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

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigFunSDK.ShowRewardedVideo(new BFRewardedVideoListener() {
                    @Override
                    public void onRewardedVideoAdClosed() {

                    }

                    @Override
                    public void onRewardedVideoAdRewarded(ISPlacement placement) {
                        Log.e("dadad",placement+"");
                    }
                });
                BigFunSDK.ShowRewardedVideo();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 */
//                BigFunSDK.BigFunLogin(MainActivity.this);
//                BigFunSDK.GooglePay(MainActivity.this, "premium_upgrade", new GooglePayUpdatedListener() {
//                    @Override
//                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
//                        Log.e("adad","1313131");
//                    }
//                });
//                BigFunSDK.BigFunGoogleLogin(MainActivity.this,"539033930849-l4gkqgs24fm72fh2hu5u9bkn2csajn6l.apps.googleusercontent.com");
                BigFunSDK.BigFunLogin(MainActivity.this);
            }
        });

    }



    /**
     * 广告释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BigFunSDK.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BigFunSDK.onActivityResult(requestCode, resultCode, data);
        Log.e("DemoActivity", "requestCode=" + requestCode + " resultCode=" + resultCode+" data=" + data.toString());
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            shareFileUrl = data.getData();
            tvShareFileUri.setText(shareFileUrl.toString());

        } else if (requestCode == REQUEST_SHARE_FILE_CODE) {
            // todo share complete.
        }else if(requestCode==BigFunSDK.SIGN_GP_LOGIN){
            Log.e("1231231",BigFunSDK.onResult(requestCode, resultCode, data));
        }
//        else if(requestCode == BigFunSDK.SIGN_LOGIN) {
//                try {
//                    SignInCredential credential = BigFunSDK.BigFunIdentity().getSignInCredentialFromIntent(data);
//                    Log.e("credential",credential.getId());
//                } catch (ApiException e) {
//                    // The ApiException status code indicates the detailed failure reason.
//                    Log.e("asdadasd",e.getMessage());
//            }
//        }
    }

}