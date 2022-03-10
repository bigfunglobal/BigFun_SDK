package com.bigfun.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.OnAttributionChangedListener;


import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.bigfun.sdk.NetWork.BFRewardedVideoListener;
import com.bigfun.sdk.NetWork.SourceNetWork;

import com.bigfun.sdk.google.GoogleCommodityListener;
import com.bigfun.sdk.google.GoogleConsumePurchaseListener;
import com.bigfun.sdk.google.GoogleQueryPayListener;
import com.bigfun.sdk.google.GoogleQueryPurchaseListener;
import com.bigfun.sdk.google.MyBillingImpl;
import com.bigfun.sdk.login.LoginListener;
import com.bigfun.sdk.login.LoginModel;
import com.bigfun.sdk.login.ShareListener;
import com.bigfun.sdk.model.BigFunViewModel;
import com.bigfun.sdk.model.SdkConfigurationInfoBean;
import com.bigfun.sdk.type.AdBFPlatForm;
import com.bigfun.sdk.type.AdBFSize;
import com.bigfun.sdk.utils.EmulatorDetector;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareContent;

import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;

import com.google.gson.Gson;
import com.tendcloud.tenddata.TalkingDataSDK;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Keep
public class BigFunSDK {

    public String mPhone = "";
    public static Context mContext;
    public static String mChannel, mChannelCode;
    private static BigFunSDK instance;
    public static final String TAG = "BIgFunSDk";
    private static Application mApplication;
    public static boolean isIn = false;
    private static long rgqwtime = 0;
    public final static int SIGN_LOGIN = 1001;

    //    private MyBillingImpl myBilling;
    private static GetSignInIntentRequest mGetSignInIntentRequest;
    private static JSONObject fbgv = new JSONObject();

    //获取时间
    public static long xaPhax() {
        Date date = new Date(System.currentTimeMillis());
        return date.getTime();
    }

    /**
     * 是否是Debug模式
     */

    static boolean isDebug = false;

    private static long mTime;
    public static Activity mActivity;
    private static String data;
    private static final String EVENT_URL = "http://gmgateway.xiaoxiangwan.com:5702/TestAPI/TestAPIDataHandler.ashx?action=sdktestinfo";

    private BigFunSDK() {

    }

    /**
     * @param application 上下文 “必填”
     * @param //channel   短信渠道 “必填” 短信渠道由平台提供
     * @param channelCode 渠道编码 "必填" 由平台提供
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Keep
    public static void init(Application application, String channelCode) {
        mTime = System.currentTimeMillis();
        mApplication = application;
        mContext = application.getApplicationContext();
//        mChannel = channel;
        mChannelCode = channelCode;
        LoginModel.getInstance();
        MyBillingImpl.getInstance().initialize(mContext);
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });

        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityResumed(final Activity activity) {
                mActivity = activity;
                if (!isIn)
                    HttpUtils.getInstance().upload(activity);
                if (BigFunViewModel.adjust)
                    Adjust.onResume();

//                IronSource.onResume(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {
                mActivity = activity;
                if (BigFunViewModel.adjust)
                    Adjust.onPause();

//                IronSource.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                mActivity = activity;
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mActivity = activity;
            }
        });

//        myBilling = new MyBillingImpl();
        HttpUtils.getInstance().bigfunsdk(NetConstant.BINFUN_SDK, mChannelCode, new ResponseListener() {
            @Override
            public void onSuccess() {

                data = (String) SPUtils.getInstance().get(BigFunSDK.mContext, Constant.KEY_DATA, "");
                LogUtils.log(data);
                SdkConfigurationInfoBean bean =
                        new Gson().fromJson(data, SdkConfigurationInfoBean.class);
                BigFunViewModel.getInstance().BigFunViewModelGosn(bean);
//                if (BigFunViewModel.FBnet) {
//                    audienceNetwork();
//                }
                if (BigFunViewModel.adjust) {
                    adjust(bean.getAdjustAppToken());
                }
                if (BigFunViewModel.tkdata) {
                    talkingDataSDK(bean.getTalkingDataAppId(), bean.getChannelName());
                }
                if (BigFunViewModel.fblonig || BigFunViewModel.shar) {
                    facebookSdk();
                }
                if (BigFunViewModel.google) {
                    Googleinit(bean.getGoogleClientId());
                }

                Log.e("BigFun", "tm init succeeded");
            }

            @Override
            public void onFail(String msg) {
                Log.e("BigFunSDK", msg);
            }

        });


    }


    private static void facebookSdk() {
//        if (fblonig || shar)
//            return;
        FacebookSdk.setAutoInitEnabled(true);
//        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.fullyInitialize();

    }

//    private static void audienceNetwork() {
//
//        AudienceNetworkAds.initialize(mContext);
//        AudienceNetworkInitializeHelper.initialize(mContext);
////        AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
//    }

    private static void talkingDataSDK(String talkingDataId, String TalkingDataChannelCode) {

        TalkingDataSDK.init(mContext, talkingDataId, TalkingDataChannelCode, "");
        TalkingDataSDK.setReportUncaughtExceptions(true);
    }

    private static void adjust(String adjustAppToken) {
        AdjustConfig acaaigxc = new AdjustConfig(mApplication, adjustAppToken, AdjustConfig.ENVIRONMENT_PRODUCTION);
        //获取时间
        rgqwtime = xaPhax();
        //获取Adjust的配置数据
        acaaigxc.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution atibunt) {
                try {
                    fbgv.put("network", atibunt.network);
                    fbgv.put("campaign", atibunt.campaign);
                    long afterTime = xaPhax();
                    long sub = afterTime - rgqwtime;
                    fbgv.put("timesub", sub);
                    LogUtils.log("atibunt: " + fbgv.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Adjust.onCreate(acaaigxc);

    }

    /**
     * @param context 上下文
     * @param eventId 自定义事件名称。
     * @param map     自定义事件的参数及参数取值
     */
    @Keep
    public static void onEvent(Context context, String eventId, Map map) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(context, eventId, map);
        if (BigFunViewModel.adjust)
            AdjustonEvent.TrackEvent(eventId, map);
        if (BigFunViewModel.firebase)
            FirebaseEvent.TrackEvent(context, eventId, map);
    }


    /**
     *内购商品的展示
     * @param googleCommodityListener
     */
    @Keep
    public static void googleQueryPay(GoogleCommodityListener googleCommodityListener){
        MyBillingImpl.getInstance().googleQueryPay(googleCommodityListener);
    }

    /**
     * 已购买的未消费的商品
     * @param queryPurchaseListener
     */
    @Keep
    public static void googleQueryPurchase(GoogleQueryPurchaseListener queryPurchaseListener){
        MyBillingImpl.getInstance().googleQueryPurchase(queryPurchaseListener);
    }

    /**
     * 内购商品的购买，回调确认购买
     * @param activity
     * @param skuDetails
     * @param googleQueryPayListener
     */
    @Keep
    public static void initiatePurchaseFlow(Activity activity, final SkuDetails skuDetails, GoogleQueryPayListener googleQueryPayListener){
        MyBillingImpl.getInstance().initiatePurchaseFlow(activity,skuDetails,googleQueryPayListener);
    }

    @Keep
    public static boolean insad(){
        return EmulatorDetector.with(mContext).detects();
    }

    /**
     * 消费购买的商品
     * @param purchase
     * @param purchaseListener
     */
    @Keep
    public static void consumePurchase(Purchase purchase, GoogleConsumePurchaseListener purchaseListener){
        MyBillingImpl.getInstance().consumePurchase(purchase,purchaseListener);
    }
    /**
     * @param activity
     * @param premium_upgrade
     * @param googlePayUpdatedListener
     */



    /**
     * 设置是否是Debug模式
     *
     * @param debug
     */

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    private static void Googleinit(String clientId) {
        mGetSignInIntentRequest =
                GetSignInIntentRequest.builder()
                        .setServerClientId(clientId)
                        .build();
    }

    /**
     * @param activity Activity上下文
     */
    private static Activity bactivity;

    @Keep
    public static void BigFunLogin(Activity activity) {
        bactivity = activity;
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.google || mGetSignInIntentRequest == null) {
            Log.e("BigFunSDK", "Background not set");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("BFLogin_Google", "Google");
        onEvent(mContext, "BFLogin_Google", map);
        LoginModel.Login(activity, mGetSignInIntentRequest);
    }


    @Keep
    public static SignInClient BigFunIdentity() {
        return Identity.getSignInClient(bactivity);
    }


    /**
     * facebook登录
     *
     * @param context        activity或者fragment的context “必填”
     * @param listener       登录回调
     */
    @Keep
    public static void BigFunLogin(Context context, LoginListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.fblonig) {
            Log.e("BigFunSDK", "后台未配置 Facebook登录");
            return;
        }
        List<String> permissionList = new ArrayList<>();
//        permissionList.add("public_profile");
        permissionList.add("email");
        Map<String, Object> map = new HashMap<>();
        map.put("BFLogin_FB", permissionList.toString());
        onEvent(mContext, "BFLogin_FB", map);
        LoginModel.facebookLogin(context, permissionList, listener);
    }

    /**
     * 退出Facebook登录
     */
    public static void BigFunLogout() {
        LoginModel.BigFunLogout();
    }


    /**
     * facebook分享
     *
     * @param context     activity或者fragment的context “必填”
     * @param linkContent 分享类型 “必填”
     * @param listener    分享回调
     */
    @Keep
    public static void BigFunShare(Context context, ShareContent linkContent, ShareListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.shar) {
            Log.e("BigFunSDK", "后台未配置 Facebook分享");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("linkContent", linkContent.getPageId());
        onEvent(mContext, "BFShare_FB", map);
        LoginModel.facebookShare(context, linkContent, listener);
    }

    /**
     * @param activity     必填
     * @param textContent  设置文本内容
     */
    @Keep
    public static void BigFunShare(Context activity, String textContent) {
        Map<String, Object> map = new HashMap<>();
        map.put("textContent", textContent);
        onEvent(mContext, "BFShare_SYS", map);
        new Share.Builder(activity)
                .setTextContent(textContent)
                .build()
                .shareBySystem();
    }

    @Keep
    public static void BigFunShare(Context activity, Uri shareFileUri) {
        Map<String, Object> map = new HashMap<>();
        map.put("shareFileUri", shareFileUri);
        onEvent(mContext, "BFShare_SYS", map);
        new Share.Builder(activity)
                .setShareFileUri(shareFileUri)
                .build()
                .shareBySystem();
    }

    private AdBFPlatForm FBPlatForm;

    /**
     * 插屏
     */

    @Keep
    public static void ShowInterstitialAdLoadAd() {
        if (checkSdkNotInit()) {
            return;
        }
//        FBPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.insetAdFB,BigFunViewModel.insetAdTM);
//        if(AdBFPlatForm.Facebook.equals(FBPlatForm)) {
//            if(!BigFunViewModel.FBnet){
//                Log.e("BigFunSDK","后台未配置 Facebook 广告");
//                return;
//            }
//            if(!TextUtils.isEmpty(BigFunViewModel.interstitialId)) {
//            Log.e("BigFunSDK", "后台未配置 插屏广告 id");
//            return;
//        }
//            AdNetwork.getInstance().interstitialAdLoadAd(mActivity, BigFunViewModel.interstitialId);
//        }else if(AdBFPlatForm.TigerMedia.equals(FBPlatForm)){
////            Map<String, Object> map = new HashMap<>();
////            map.put("placementId", BigFunViewModel.interstitialId);
////            map.put("adBFPlatForm", "TigerMedia");
////            onEvent(mContext, "BFAd_TM_Interstitial", map);
        if (!BigFunViewModel.ISoure) {
            Log.e("BigFunSDK", "后台未配置 IronSource 广告");
            return;
        }
        SourceNetWork.showInterstitial();
//        }

    }

    /**
     * 奖励视屏
     */

    @Keep
    public static void ShowRewardedVideo(BFRewardedVideoListener listener) {

        if (checkSdkNotInit()) {
            return;
        }
//        FBPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.incentiveVideoFB,BigFunViewModel.incentiveVideoTM);
//        if(!BigFunViewModel.FBnet){
//            Log.e("BigFunSDK","后台未配置 Facebook 广告");
//            return;
//        }
//        if(!TextUtils.isEmpty(BigFunViewModel.rewardedVideoId)) {
//            Log.e("BigFunSDK", "后台未配置 奖励式视频广告 id");
//            return;
//        }
//
//        if(AdBFPlatForm.Facebook.equals(FBPlatForm)) {
//            AdNetwork.getInstance().rewardedVideoLoadAd(mActivity, BigFunViewModel.rewardedVideoId, listener);
//        }else if(AdBFPlatForm.TigerMedia.equals(FBPlatForm)){
//            Map<String, Object> map = new HashMap<>();
//            map.put("placementId", BigFunViewModel.rewardedVideoId);
//            map.put("adBFPlatForm", "TigerMedia");
//            onEvent(mContext, "BFAd_TM_RewardsVedio", map);
//            GoldSource.showRewardedVideo();
        if (!BigFunViewModel.ISoure) {
            Log.e("BigFunSDK", "后台未配置 IronSource 广告");
            return;
        }
        SourceNetWork.showRewardedVideo(listener);
//        }
    }

    /**
     * 横屏
     *
     * @param mBannerParentLayout
     * @param size
     */
    @Keep
    public static void ShowBanner(FrameLayout mBannerParentLayout, AdBFSize size) {
        if (checkSdkNotInit()) {
            return;
        }
//        FBPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.streamerAdFB,BigFunViewModel.streamerAdTM);
//        if(AdBFPlatForm.Facebook.equals(FBPlatForm)) {
//            if(!BigFunViewModel.FBnet){
//                Log.e("BigFunSDK","后台未配置 Facebook 广告");
//                return;
//            }
//
//            if(!TextUtils.isEmpty(BigFunViewModel.bannerAdId)) {
//                Log.e("BigFunSDK", "后台未配置 横幅广告 id");
//                return;
//            }
//            AdNetwork.getInstance().adViewLoadAd(mActivity, BigFunViewModel.bannerAdId, mBannerParentLayout, size);
//        }else if(AdBFPlatForm.TigerMedia.equals(FBPlatForm)) {
        if (!BigFunViewModel.ISoure) {
            Log.e("BigFunSDK", "后台未配置 IronSource 广告");
            return;
        }
        SourceNetWork.createAndloadBanner(mBannerParentLayout, size);
//        }
    }

    /**
     * 资源释放
     */

    @Keep
    public static void onDestroy() {
        if (checkSdkNotInit()) {
            return;
        }
//        AdNetwork.getInstance().dstroy();
        SourceNetWork.onDestroy();
    }

    /**
     * 添加到callbackManager
     *
     * @param requestCode “必填”
     * @param resultCode  “必填”
     * @param data        “必填”
     */

    @Keep
    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LoginModel.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查是否初始化
     */
    private static boolean checkSdkNotInit() {
        if (TextUtils.isEmpty(mChannelCode) || mContext == null || !BigFunViewModel.sdk) {
            Log.e("BigFunSDK", "sdk not init");
            return true;
        }
        return false;
    }





}
