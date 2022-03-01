package com.bigfun.sdk;

import static androidx.core.app.ActivityCompat.startIntentSenderForResult;
import static com.bigfun.sdk.Constant.PAY_TAG;
import static com.bigfun.sdk.HttpUtils.ORDER_SDK;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.OnAttributionChangedListener;
import com.bigfun.sdk.NetWork.AdNetwork;
import com.bigfun.sdk.NetWork.AdViewListener;
import com.bigfun.sdk.NetWork.ISRewardedVideoListener;
import com.bigfun.sdk.NetWork.InterstListener;
import com.bigfun.sdk.NetWork.RewardVideoListener;
import com.bigfun.sdk.NetWork.SourceNetWork;
import com.bigfun.sdk.login.LoginListener;
import com.bigfun.sdk.login.ShareListener;
import com.bigfun.sdk.login.LoginModel;
import com.bigfun.sdk.model.BigFunViewModel;
import com.bigfun.sdk.model.SdkConfigurationInfoBean;
import com.bigfun.sdk.type.AdBFPlatForm;
import com.bigfun.sdk.type.AdBFSize;
import com.bigfun.sdk.type.ShareContentType;
import com.bigfun.sdk.utils.Distribution_es;
import com.bigfun.sdk.utils.LocationUtils;
import com.bigfun.sdk.utils.SystemUtil;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;

import com.google.gson.Gson;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.tendcloud.tenddata.TalkingDataSDK;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;


@Keep
public class BigFunSDK {

    public String mPhone = "";
    public static Context mContext;
    public static String mChannel,mChannelCode;
    private static BigFunSDK instance;
    public static final String TAG = "BIgFunSDk";
    private static Application mApplication;
    public static boolean isIn=false;
    private static long rgqwtime = 0;
    public final static int SIGN_LOGIN=1001;

//    private MyBillingImpl myBilling;
    private GetSignInIntentRequest mGetSignInIntentRequest;
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

    private long mTime;
    private static Activity mActivity;
    private String data;
    private static final String EVENT_URL = "http://gmgateway.xiaoxiangwan.com:5702/TestAPI/TestAPIDataHandler.ashx?action=sdktestinfo";

    private BigFunSDK() {

    }

    /**
     * @param application 上下文 “必填”
     * @param //channel     短信渠道 “必填” 短信渠道由平台提供
     * @param channelCode 渠道编码 "必填" 由平台提供
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Keep
    public void init(Application application, String channelCode) {
        mTime = System.currentTimeMillis();
        mApplication = application;
        mContext = application.getApplicationContext();
//        mChannel = channel;
        mChannelCode = channelCode;
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK",throwable.getMessage());
            }
        });

//        myBilling = new MyBillingImpl();
        HttpUtils.getInstance().bigfunsdk(NetConstant.BINFUN_SDK, mChannelCode, new ResponseListener() {
            @Override
            public void onSuccess() {

                data = (String) SPUtils.getInstance().get(BigFunSDK.mContext, Constant.KEY_DATA, "");
                SdkConfigurationInfoBean bean =
                        new Gson().fromJson(data, SdkConfigurationInfoBean.class);
                BigFunViewModel.getInstance().BigFunViewModelGosn(bean);
                if (BigFunViewModel.adnet) {
                    audienceNetwork();
                }
                if(BigFunViewModel.adjust){
                    adjust(bean.getAdjustAppToken());
                }
                if(BigFunViewModel.tkdata){
                    talkingDataSDK(bean.getTalkingDataAppId(), bean.getChannelName());
                }
                if(BigFunViewModel.fblonig||BigFunViewModel.shar){
                    facebookSdk();
                }
                if(BigFunViewModel.google){
                    Googleinit(bean.getGoogleClientId());
                }
            }

            @Override
            public void onFail(String msg) {
                Log.e("BigFunSDK", msg);
            }

        });
//        myBilling.initialize(mContext);

//        initLogin();
        //是否已经归因
//        LogUtils.log("sdk init success");
        Log.e("BigFun", "tm init succeeded");
    }


    private static void facebookSdk() {
//        if (fblonig || shar)
//            return;
//        FacebookSdk.setAutoInitEnabled(true);
//        FacebookSdk.setIsDebugEnabled(true);
//        FacebookSdk.fullyInitialize();

    }

    private static void audienceNetwork() {

        AudienceNetworkAds.initialize(mContext);
        AudienceNetworkInitializeHelper.initialize(mContext);
//        AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
    }

    private static void talkingDataSDK(String talkingDataId, String TalkingDataChannelCode) {
//        TalkingDataGA.init(application, TalkingDatId, TalkingDatChannelCode);
//        TDGAProfile.setProfile(TalkingDataGA.getDeviceId(application));
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
//                    fbgv.put("trackerName",atibunt.trackerName);
                    fbgv.put("network", atibunt.network);
                    fbgv.put("campaign", atibunt.campaign);
                    long afterTime = xaPhax();
                    long sub = afterTime - rgqwtime;
                    fbgv.put("timesub", sub);
//                    SharedPreferences sp = application.getSharedPreferences(application.getPackageName() + "_switchvalue", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("adAttri", fbgv.toString());
//                    editor.commit();
//                    TalkingDataEvent.WKeeNM(mContext, "A_Ev_Adgy", "gyInfo", fbgv.toString());
//                    TalkingDataEvent.WKeeNM(mContext, "A_Ev_Adgy", "attrInfo", atibunt.toString());
                    Log.d("staApplication", "atibunt: " + fbgv.toString());
//                    if (sp.getInt("completeRef", 0) == 2) {
//                        已经获取gogglereffer了
//                        Log.e("TAGerf", "mkit6: ");
//                        editor.putInt("completeADJ", 1);
//                        editor.commit();
//                        xqnEwo();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Adjust.onCreate(acaaigxc);
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(final Activity activity) {
                mActivity=activity;
                if(!isIn)
                    HttpUtils.getInstance().upload(activity);

                if(BigFunViewModel.adjust)
                    Adjust.onResume();
            }

            @Override
            public void onActivityPaused(Activity activity) {

                if(BigFunViewModel.adjust)
                    Adjust.onPause();
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * @param context   上下文
     * @param eventId   自定义事件名称。
     * @param map 自定义事件的参数及参数取值
     */
    @Keep
    public void onEvent(Context context, String eventId,Map map) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(context, eventId, map);
        if (BigFunViewModel.adjust)
            AdjustonEvent.TrackEvent(eventId, map);
        if(BigFunViewModel.firebase)
            FirebaseEvent.TrackEvent(context,eventId, map);
    }


//    @Keep
//    public void init(Context context, String channel, IAttributionListener listener) {
//        mTime = System.currentTimeMillis();
//        mContext = context;
//        mChannel = channel;
////        mListener = listener;
//        checkSdkNotInit();
//        initLogin();
//        //是否已经归因
////        LogUtils.log("sdk init success");
//    }

    /**
     * @param activity
     * @param premium_upgrade
     * @param googlePayUpdatedListener
     */
//    @Keep
//    public void GooglePay(Activity activity, String premium_upgrade, GooglePayUpdatedListener googlePayUpdatedListener) {
//        if (checkSdkNotInit()) {
//            return;
//        }
//        myBilling.googlepay(activity, premium_upgrade, googlePayUpdatedListener);
//    }

    /**
     * 设置是否是Debug模式
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    @Keep
    public static BigFunSDK getInstance() {
        if (instance == null) {
            synchronized (BigFunSDK.class) {
                if (instance == null) {
                    instance = new BigFunSDK();
                }
            }
        }
        return instance;
    }

    private static FragmentActivity activity;

    private void Googleinit(String clientId){
        mGetSignInIntentRequest =
                GetSignInIntentRequest.builder()
                        .setServerClientId(clientId)
                        .build();
    }

    /**
     *
     * @param activity Activity上下文
     */
    @Keep
    public void BigFunGoogleLogin(FragmentActivity activity){
        this.activity=activity;
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.google||mGetSignInIntentRequest==null){
            Log.e("BigFunSDK", "Background not set");
            return ;
        }

        Map<String,Object> map=new HashMap<>();
        map.put("BFLogin_Google","Google");
        onEvent(mContext,"BFLogin_Google",map);

        Identity.getSignInClient(activity)
                .getSignInIntent(mGetSignInIntentRequest)
                .addOnSuccessListener(
                        result -> {
                            try {
                                activity.startIntentSenderForResult(
                                        result.getIntentSender(),
                                        SIGN_LOGIN,
                                        /* fillInIntent= */ null,
                                        /* flagsMask= */ 0,
                                        /* flagsValue= */ 0,
                                        /* extraFlags= */ 0,
                                        /* options= */ null);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e("BigFunSDK", "Google Sign-in failed");
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            Log.e("BigFunSDK", "Google Sign-in failed", e);
                        });

    }

    @Keep
    public SignInClient BigFunIdentity(){
        return  Identity.getSignInClient(activity);
    }



    /**
     * facebook登录
     *
     * @param context        activity或者fragment的context “必填”
     * @param permissionList 登录配置 “必填”
     * @param listener       登录回调
     */
    @Keep
    public void BigFunFBLogin(Context context, List<String> permissionList, LoginListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.fblonig) {
            Log.e("BigFunSDK","后台未配置 Facebook登录");
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("BFLogin_FB",permissionList.toString());
        onEvent(mContext,"BFLogin_FB",map);
        LoginModel.getInstance().facebookLogin(context, permissionList, listener);
    }

    /**
     * 退出Facebook登录
     */
    public void BigFunLogout() {

        Map<String,Object> map=new HashMap<>();
        map.put("BFLogout_FB","BFLogout_FB");
        onEvent(mContext,"BFLogout_FB",map);
        //退出facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            //判断Facebook是否登录  如果登录先退出
            try {
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * facebook分享
     *
     * @param context     activity或者fragment的context “必填”
     * @param linkContent 分享类型 “必填”
     * @param listener    分享回调
     */
    @Keep
    public void BigFunFBShare(Context context, ShareLinkContent linkContent, ShareListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.shar) {
            Log.e("BigFunSDK","后台未配置 Facebook分享");
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("linkContent",linkContent.getQuote());
        onEvent(mContext,"BFShare_FB",map);
        LoginModel.getInstance().facebookShare(context, linkContent, listener);
    }

    /**
     * @param activity     必填
     * @param contentType  分享数据类型 必填
     * @param shareFileUri 设置分享文件路径
     * @param textContent  设置文本内容
     * @param title        设置标题
     * @param requestCode  置为ActivityResult requestCode，默认值为-1
     */
    @Keep
    public void BigFunShare(Activity activity, @ShareContentType String contentType, Uri shareFileUri, String textContent, @NonNull String title, int requestCode) {
        Map<String,Object> map=new HashMap<>();
        map.put("contentType",contentType);
        map.put("shareFileUri",shareFileUri);
        map.put("textContent",textContent);
        onEvent(mContext,"BFShare_SYS",map);
        switch (contentType) {
            case ShareContentType.TEXT:
                new Share.Builder(activity)
                        .setContentType(contentType)
                        .setTextContent(textContent)
                        .setTitle(title)
//                    .setShareFileUri(shareFileUri)
                        .setOnActivityResult(requestCode)
                        .build()
                        .shareBySystem();
                break;
            case ShareContentType.IMAGE:
                new Share.Builder(activity)
                        .setContentType(ShareContentType.IMAGE)
                        .setShareFileUri(shareFileUri)
                        //.setShareToComponent("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI")
                        .setTitle("Share Image")
                        .build()
                        .shareBySystem();
                break;
            case ShareContentType.AUDIO:
                new Share.Builder(activity)
                        .setContentType(ShareContentType.AUDIO)
                        .setShareFileUri(shareFileUri)
                        .setTitle("Share Audio")
                        .build()
                        .shareBySystem();
                break;
            case ShareContentType.VIDEO:
                new Share.Builder(activity)
                        .setContentType(ShareContentType.VIDEO)
                        .setShareFileUri(shareFileUri)
                        .setTitle("Share Video")
                        .build()
                        .shareBySystem();
                break;
            case ShareContentType.FILE:
                new Share.Builder(activity)
                        .setContentType(ShareContentType.FILE)
                        .setShareFileUri(shareFileUri)
                        .setTitle("Share File")
                        .setOnActivityResult(requestCode)
                        .build()
                        .shareBySystem();
                break;

            default:
                break;

        }

    }

    /**
     * 奖励式视频广告
     */
//    @Keep
//    public void showRewardedVideo(){
//        Map<String,Object> map=new HashMap<>();
//        map.put("RewardsVedio","TM");
//        onEvent(mContext,"BFAd_TM_RewardsVedio",map);
//        GoldSource.showRewardedVideo();
//    }

    /**
     * 插屏广告
     */
//    @Keep
//    public void showInterstitial(Context context){
////        Map<String,Object> map=new HashMap<>();
////        map.put("Interstitial","TM");
////        onEvent(mContext,"BFAd_TM_Interstitial",map);
////        GoldSource.showInterstitial();
////        InterstitialAd interstitialAd=new InterstitialAd(context,"PLAYABLE#1279284552593528_1281077885747528");
////        InterstitialAdListener interstitialAdListener=new InterstitialAdListener() {
////            @Override
////            public void onInterstitialDismissed(Ad ad) {
////
////            }
////
////            @Override
////            public void onInterstitialDisplayed(Ad ad) {
////
////            }
////
////            @Override
////            public void onError(Ad ad, AdError adError) {
////                Log.e("adError",adError.getErrorMessage());
////            }
////
////            @Override
////            public void onAdLoaded(Ad ad) {
////                interstitialAd.show();
////            }
////
////            @Override
////            public void onAdClicked(Ad ad) {
////
////            }
////
////            @Override
////            public void onLoggingImpression(Ad ad) {
////
////            }
////        };
////        InterstitialAd.InterstitialLoadAdConfig lo=interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build();
////        interstitialAd.loadAd(lo);
//    }


    /**
     * 横幅广告
     *
     * @param context     上下文 “必填”
     * @param adContainer 横幅广告的布局 “必填”
     * @param listener    广告回调
     * @param adBFPlatForm    广告类型
     * @param AdsID    广告id
     */

    @Keep
    public void AdViewLoadAd(Context context, AdBFPlatForm adBFPlatForm,String AdsID, LinearLayout adContainer, AdBFSize adSize, AdViewListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
//        if(adBFPlatForm.equals(AdBFPlatForm.Facebook))

        if(!TextUtils.isEmpty(AdsID))
            BigFunViewModel.bannerAdId=AdsID;
        else {
            Log.e("BigFunSDK","AdsID not null");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.insetAdFB,BigFunViewModel.insetAdTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {
            Map<String, Object> map = new HashMap<>();
            map.put("placementId", BigFunViewModel.bannerAdId);
            map.put("adBFPlatForm", adBFPlatForm);
            map.put("adSize", adSize);
            onEvent(mContext, "BFAd_FB_Banner", map);
            AdNetwork.getInstance().adViewLoadAd(context, BigFunViewModel.bannerAdId, adContainer, adSize, listener);
        }
    }
    /**
     * 横幅广告
     *
     * @param context     上下文 “必填”
     * @param adContainer 横幅广告的布局 “必填”
     * @param listener    广告回调
     * @param adBFPlatForm    广告类型
     */
    @Keep
    public void AdViewLoadAd(Context context, AdBFPlatForm adBFPlatForm, LinearLayout adContainer, AdBFSize adSize, AdViewListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
//        if(adBFPlatForm.equals(AdBFPlatForm.Facebook))

        if(!TextUtils.isEmpty(BigFunViewModel.bannerAdId)) {
            Log.e("BigFunSDK", "后台未配置 横幅广告 id");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.insetAdFB,BigFunViewModel.insetAdTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {

            AdNetwork.getInstance().adViewLoadAd(context, BigFunViewModel.bannerAdId, adContainer, adSize, listener);
        }
    }

    /**
     * 奖励式视频广告
     *
     * @param context  上下文
     * @param listener 广告回调
     * @param adBFPlatForm    广告类型
     * @param AdsID    广告id
     */
    @Keep
    public void RewardedVideoLoadAd(Context context, AdBFPlatForm adBFPlatForm,String AdsID, RewardVideoListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
        if(!TextUtils.isEmpty(AdsID))
            BigFunViewModel.rewardedVideoId=AdsID;
        else {
            Log.e("BigFunSDK","AdsID not null");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.incentiveVideoFB,BigFunViewModel.incentiveVideoTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {
            AdNetwork.getInstance().rewardedVideoLoadAd(context, BigFunViewModel.rewardedVideoId, listener);
        }
        else if(AdBFPlatForm.TigerMedia.equals(adBFPlatForm)){
            Map<String, Object> map = new HashMap<>();
            map.put("placementId", BigFunViewModel.rewardedVideoId);
            map.put("adBFPlatForm", adBFPlatForm);
            onEvent(mContext, "BFAd_TM_RewardsVedio", map);
//            GoldSource.showRewardedVideo();
        }
    }

    /**
     * 奖励式视频广告
     *
     * @param context       上下文 “必填”
     * @param listener      广告回调
     * @param adBFPlatForm    广告类型
     */
    @Keep
    public void RewardedVideoLoadAd(Context context, AdBFPlatForm adBFPlatForm, RewardVideoListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
        if(!TextUtils.isEmpty(BigFunViewModel.rewardedVideoId)) {
            Log.e("BigFunSDK", "后台未配置 奖励式视频广告 id");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.incentiveVideoFB,BigFunViewModel.incentiveVideoTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {
            AdNetwork.getInstance().rewardedVideoLoadAd(context, BigFunViewModel.rewardedVideoId, listener);
        }else if(AdBFPlatForm.TigerMedia.equals(adBFPlatForm)){
            Map<String, Object> map = new HashMap<>();
            map.put("placementId", BigFunViewModel.rewardedVideoId);
            map.put("adBFPlatForm", adBFPlatForm);
            onEvent(mContext, "BFAd_TM_RewardsVedio", map);
//            GoldSource.showRewardedVideo();
        }
    }




    /**
     * 插屏广告
     *
     * @param context       上下文 “必填”
     * @param listener      广告回调
     * @param adBFPlatForm    广告类型
     * @param AdsID    广告id
     */
    @Keep
    public void InterstitialAdLoadAd(Context context,AdBFPlatForm adBFPlatForm,String AdsID, InterstListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
        if(!TextUtils.isEmpty(AdsID))
            BigFunViewModel.interstitialId=AdsID;
        else {
            Log.e("BigFunSDK","AdsID not null");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.streamerAdFB,BigFunViewModel.streamerAdTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {
            AdNetwork.getInstance().interstitialAdLoadAd(context, BigFunViewModel.interstitialId, listener);
        }else if(AdBFPlatForm.TigerMedia.equals(adBFPlatForm)){
            Map<String, Object> map = new HashMap<>();
            map.put("placementId", BigFunViewModel.interstitialId);
            map.put("adBFPlatForm", adBFPlatForm);
            onEvent(mContext, "BFAd_TM_Interstitial", map);
//            GoldSource.showInterstitial();
        }
    }

    /**
     * 插屏广告
     *
     * @param context       上下文 “必填”
     * @param listener      广告回调
     * @param adBFPlatForm    广告类型
     */
    @Keep
    public void InterstitialAdLoadAd(Context context,AdBFPlatForm adBFPlatForm, InterstListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if(!BigFunViewModel.adnet){
            Log.e("BigFunSDK","后台未配置 Facebook 广告");
            return;
        }
        if(!TextUtils.isEmpty(BigFunViewModel.interstitialId)) {
            Log.e("BigFunSDK", "后台未配置 插屏广告 id");
            return;
        }
        adBFPlatForm=Distribution_es.RandomMooncake(BigFunViewModel.streamerAdFB,BigFunViewModel.streamerAdTM);
        if(AdBFPlatForm.Facebook.equals(adBFPlatForm)) {
            AdNetwork.getInstance().interstitialAdLoadAd(context, BigFunViewModel.interstitialId, listener);
        }else if(AdBFPlatForm.TigerMedia.equals(adBFPlatForm)){
            Map<String, Object> map = new HashMap<>();
            map.put("placementId", BigFunViewModel.interstitialId);
            map.put("adBFPlatForm", adBFPlatForm);
            onEvent(mContext, "BFAd_TM_Interstitial", map);
        }
    }

    /**
     * IS的插屏
     */
    @Keep
    public void ISourceShowInterstitialAdLoadAd(Activity activity){
        SourceNetWork.getInstance(activity).showInterstitial();
    }
    /**
     * IS的奖励视屏
     */
    @Keep
    public void ISourceShowRewardedVideo(Activity activity,ISRewardedVideoListener listener){
        SourceNetWork.getInstance(activity).showRewardedVideo(listener);
    }
    @Keep
    public void ISourceShowBanner(Activity activity, FrameLayout mBannerParentLayout, AdBFSize size){
        SourceNetWork.getInstance(activity).createAndloadBanner(activity,mBannerParentLayout,size);
    }

    /**
     * 资源释放
     */

    @Keep
    public void onDestroy() {
        if (checkSdkNotInit()) {
            return;
        }
        AdNetwork.getInstance().dstroy();
        SourceNetWork.getInstance(mActivity).onDestroy();
    }



    /**
     * 添加到callbackManager
     *
     * @param requestCode “必填”
     * @param resultCode  “必填”
     * @param data        “必填”
     */
    @Keep
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LoginModel.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查是否初始化
     */
    private boolean checkSdkNotInit() {
        if (TextUtils.isEmpty(mChannelCode) || mContext == null || !BigFunViewModel.sdk) {
            Log.e("BigFunSDK","sdk not init");
            return true;
        }
        return false;
    }


    /**
     * 发送短信
     *
     * @param params   号码
     * @param listener 回调
     */
//    @Keep
//    public void sendSms(Map<String, Object> params, ResponseListener listener) {
//        if (checkSdkNotInit()) {
//            return;
//        }
//        if (!sms) {
//            return;
//        }
//        if (!params.containsKey("mobile")) {
//            throw new IllegalArgumentException(PAY_TAG + "mobile is required");
//        }
//        mPhone = params.get("mobile").toString();
//        if (mPhone.length() != 12 && mPhone.length() != 10) {
//            Toast.makeText(
//                    mContext,
//                    "Please fill in the correct phone number",
//                    Toast.LENGTH_SHORT
//            ).show();
//            return;
//        }
//        String phone;
//        if (mPhone.startsWith("91")) {
//            phone = mPhone;
//        } else {
//            phone = "91" + mPhone;
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.putAll(params);
//        map.put("codeType", 2);
//        map.put("channelCode", mChannel);
//        HttpUtils.getInstance().sendSms(NetConstant.REPORT_URL, map, listener);
//    }


    /**
     * 手机号+验证码登录
     *
     * @param params
     * @param listener
     */
//    @Keep
//    public void loginWithCode(Map<String, Object> params, ResponseListener listener) {
//        if (checkSdkNotInit()) {
//            return;
//        }
//        if (!params.containsKey("mobile") || !params.containsKey("code") ||
//                TextUtils.isEmpty(params.get("mobile").toString()) ||
//                TextUtils.isEmpty(params.get("code").toString())) {
//            listener.onFail("缺少参数");
//            return;
//        }
//        if (TextUtils.isEmpty(HttpUtils.mCode)) {
//            listener.onFail("请先获取验证码");
//            return;
//        }
//        if (!HttpUtils.mCode.equals(params.get("code")) || !HttpUtils.mPhone.equals(params.get("mobile"))) {
//            listener.onFail("验证码错误或者验证码与上一次获取的手机号不一致");
//            return;
//        }
//        listener.onSuccess();
//    }

}
