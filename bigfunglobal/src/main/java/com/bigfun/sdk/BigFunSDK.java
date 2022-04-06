package com.bigfun.sdk;

import static com.bigfun.sdk.model.BigFunViewModel.goopay;
import static com.bigfun.sdk.utils.Utils.isNetSystemUsable;
import static com.bigfun.sdk.utils.Utils.netWorkIsEnable;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Keep;
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
import com.bigfun.sdk.listener.BFSuccessListener;
import com.bigfun.sdk.listener.GoogleCommodityListener;
import com.bigfun.sdk.listener.GoogleConsumePurchaseListener;
import com.bigfun.sdk.listener.GoogleQueryPayListener;
import com.bigfun.sdk.listener.GoogleQueryPurchaseListener;
import com.bigfun.sdk.google.MyBillingImpl;
import com.bigfun.sdk.listener.ResponseListener;
import com.bigfun.sdk.listener.BFAdjustListener;
import com.bigfun.sdk.listener.LoginListener;
import com.bigfun.sdk.login.LoginModel;
import com.bigfun.sdk.listener.ShareListener;
import com.bigfun.sdk.model.BigFunViewModel;
import com.bigfun.sdk.model.GoogleBean;
import com.bigfun.sdk.model.IPBean;
import com.bigfun.sdk.model.SdkConfigurationInfoBean;
import com.bigfun.sdk.type.AdBFPlatForm;
import com.bigfun.sdk.type.AdBFSize;
import com.bigfun.sdk.utils.Distribution_es;
import com.bigfun.sdk.utils.EmulatorDetector;
import com.bigfun.sdk.utils.HttpUtils;
import com.bigfun.sdk.utils.IpUtils;
import com.bigfun.sdk.utils.LocationUtils;
import com.bigfun.sdk.utils.SystemUtil;
import com.bigfun.sdk.utils.dsjcfjoc;
import com.facebook.FacebookSdk;

import com.facebook.LoggingBehavior;
import com.facebook.share.model.ShareContent;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.ironsource.mediationsdk.IronSource;
import com.tendcloud.tenddata.TCAgent;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Keep
public class BigFunSDK {

    public static String mPhone = "";
    public static Context mContext;
    public static String mChannel, mChannelCode;
    private static BigFunSDK instance;
    public static final String TAG = "BIgFunSDk";
    private static Application mApplication;
    public static boolean isIn = false, ipbs = false;
    private static long rgqwtime = 0;
    private static IPBean ipBean;
    private static String bfip = "", cyoua = "";
    public final static int SIGN_GP_LOGIN = 1001;
    public final static int SUT_BF = 1010;

    //    private MyBillingImpl myBilling;
//    private static GetSignInIntentRequest mGetSignInIntentRequest;
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
    static String environment="";

    private static long mTime;
    public static Activity mActivity;
    private static String data;
    private static String TalkingDataAppId;
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
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });

        BFinit(null, null);

    }

    /**
     * @param application 上下文 “必填”
     * @param //channel   短信渠道 “必填” 短信渠道由平台提供
     * @param channelCode 渠道编码 "必填" 由平台提供
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Keep
    public static void init(Application application, String channelCode, BFAdjustListener listener) {
        mTime = System.currentTimeMillis();
        mApplication = application;
        mContext = application.getApplicationContext();
//        mChannel = channel;
        mChannelCode = channelCode;
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });

        BFinit(listener, null);

    }

    /**
     * @param application 上下文 “必填”
     * @param //channel   短信渠道 “必填” 短信渠道由平台提供
     * @param channelCode 渠道编码 "必填" 由平台提供
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Keep
    public static void init(Application application, String channelCode, BFSuccessListener bfSuccessListener) {
        mTime = System.currentTimeMillis();
        mApplication = application;
        mContext = application.getApplicationContext();
//        mChannel = channel;
        mChannelCode = channelCode;
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });

        BFinit(null, bfSuccessListener);

    }

    /**
     * @param application 上下文 “必填”
     * @param //channel   短信渠道 “必填” 短信渠道由平台提供
     * @param channelCode 渠道编码 "必填" 由平台提供
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Keep
    public static void init(Application application, String channelCode, BFAdjustListener listener, BFSuccessListener bfSuccessListener) {
        mTime = System.currentTimeMillis();
        mApplication = application;
        mContext = application.getApplicationContext();
        mChannelCode = channelCode;
//        mChannel = channel;
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });

        BFinit(listener, bfSuccessListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void BFinit(BFAdjustListener listener, BFSuccessListener bfSuccessListener) {
        environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        //IS广告SDK,与TM广告不能共存
        SourceNetWork.initListener(bfSuccessListener);
        //TM广告SDK,与IS广告不能共存
//        TMNetWork.init();
        LoginModel.getInstance();
        MyBillingImpl.getInstance().initialize(mContext);
//        bfip = dsjcfjoc.getIpAddress(mContext);
//        cyoua = IpUtils.getOutNetIP(mContext, bfip);
//        HttpUtils.getInstance().bigfunip(NetConstant.BF_IP_URL, mChannelCode, new ResponseListener() {
//            @Override
//            public void onSuccess(String data) {
//                ipbs = true;
//                LogUtils.log(data);
//                ipBean =
//                        new Gson().fromJson(data, IPBean.class);
////                fhfioafm();
//            }
//
//            @Override
//            public void onFail(String msg) {
//                Log.e("asdad", msg);
//            }
//        });
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
                if (!isIn) {
                    HttpUtils.getInstance().upload(activity);
                }
                if (BigFunViewModel.adjust)
                    Adjust.onResume();


//                if(!isNetSystemUsable(mContext)&&!netWorkIsEnable(mContext)){
//                    Dialog dialog=new Dialog(activity);
//                    View view = LayoutInflater.from(activity).inflate(R.layout.jlsaodj, null);
//
//                    dialog.setContentView(view, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,
//                            ViewGroup.MarginLayoutParams.MATCH_PARENT));
//                    dialog.setCancelable(false);
//                    view.findViewById(R.id.oekacx).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent =  new Intent(Settings.ACTION_SETTINGS);
//                            activity.startActivity(intent);
//                            dialog.dismiss();
//                        }
//                    });
//                    view.findViewById(R.id.urjasod).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//
//                }
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


        HttpUtils.getInstance().bigfunsdk(NetConstant.BINFUN_SDK, mChannelCode, new ResponseListener() {
            @Override
            public void onSuccess(String data) {

                LogUtils.log(data);
                SdkConfigurationInfoBean bean =
                        new Gson().fromJson(data, SdkConfigurationInfoBean.class);
                TalkingDataAppId = bean.getTalkingDataAppId();
                BigFunViewModel.getInstance().BigFunViewModelGosn(bean);

                if (BigFunViewModel.adjust) {
                    adjust(bean.getAdjustAppToken(), listener);
                }
                if (BigFunViewModel.tkdata) {
                    talkingDataSDK(bean.getTalkingDataAppId());
                }
                if (BigFunViewModel.fblonig || BigFunViewModel.shar) {
                    facebookSdk();
                }
                if (BigFunViewModel.ISoure) {
                    SourceNetWork.getInstance().TimerIronSource();
                }
                if (BigFunViewModel.google&&!bean.getGoogleClientId().isEmpty()) {
                    Googleinit(bean.getGoogleClientId());
                }
//                if(BigFunViewModel.TMnet) {
//                    GoldSource.initialize(mContext, bean.getCompAny(), bean.getIronSourceAppKey(), new GoldListener() {
//                        @Override
//                        public void onInitializationCompleted() {
//                            if(bfSuccessListener!=null)
//                                bfSuccessListener.onSuccess();
//                            LogUtils.log("tm init succeeded");
//                        }
//                    });
//                }
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
//        FacebookSdk.sdkInitialize(mContext);
        FacebookSdk.setAutoInitEnabled(true);
//        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        FacebookSdk.fullyInitialize();
    }


    private static void talkingDataSDK(String talkingDataId) {
        TCAgent.init(mContext, talkingDataId, "play.google.com");
        TCAgent.setProfileId(TCAgent.getDeviceId(mContext));
        TCAgent.setReportUncaughtExceptions(true);
    }

    private static void adjust(String adjustAppToken, BFAdjustListener listener) {
        Log.e("environment",environment);
        AdjustConfig acaaigxc = new AdjustConfig(mApplication, adjustAppToken, environment);
        //获取时间
        rgqwtime = xaPhax();
        //获取Adjust的配置数据
        acaaigxc.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution atibunt) {
                if (listener != null)
                    listener.onAttributionChanged(atibunt);
                try {
                    fbgv.put("network", atibunt.network);
                    fbgv.put("campaign", atibunt.campaign);
                    long afterTime = xaPhax();
                    long sub = afterTime - rgqwtime;
                    fbgv.put("timesub", sub);
                    LogUtils.log("atibunt: " + fbgv.toString());
                    onEvent("bg_Adgy", fbgv.toString());
                    onEvent("bg_Atibunt", atibunt.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Adjust.onCreate(acaaigxc);

    }

    /**
     * 是否真机
     *
     * @return
     */
    public static boolean fictitious() {
        return EmulatorDetector.with(mContext).detects();
    }

    public static String getTDID() {
        return TalkingDataAppId;
    }

    /**
     * 手机设备信息
     *
     * @return
     */

    public static String SuspiciousEquipment() {
        Map<String, String> map = new HashMap<>();
        map.put("model", SystemUtil.getInstance(mContext).getModel());
        map.put("versionName", SystemUtil.getInstance(mContext).getVersion());
        map.put("ip", dsjcfjoc.getIpAddress(mContext));
        map.put("packageName", SystemUtil.getInstance(mContext).getPackageName());
        map.put("resolution", SystemUtil.getInstance(mContext).getResolution());
        map.put("networkType", SystemUtil.getInstance(mContext).getNetWorkType());
        map.put("gps", LocationUtils.getInstance(mContext).initLocation());
        return map.toString();
    }

    /**
     * 返回后台设置区域，IP的真金模式
     *
     * @return
     */
//    public static boolean fhfioafm() {
//        if (ipbs) {
//            if (!ipBean.isIpWhitelist()) {
//                if (!ipBean.getBlackList().isEmpty() && ipBean.getBlackList().contains(bfip)) {
//                    return false;
//                } else if (!ipBean.getWhiteList().isEmpty() && ipBean.getWhiteList().contains(bfip)) {
//                    return true;
//                }
//
//                if (!TextUtils.isEmpty(ipBean.getArea())) {
//                    try {
//                        JSONObject jo = new JSONObject(cyoua);
//                        if (Distribution_es.adadawco(ipBean.getArea()).contains(jo.getString("country_code"))) {
//                            return true;
//                        } else if (Distribution_es.asdaod(ipBean.getArea()).contains(jo.getString("region"))) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                }
//
//            } else
//                return false;
//        }
//        return false;
//    }

    //
    public static String getDeviceId() {
        return TCAgent.getDeviceId(mContext);
    }


    public static String getOAID() {
        return TCAgent.getOAID(mContext);
    }


    /**
     * @param context 上下文
     * @param eventId 自定义事件名称。
     * @param map     自定义事件的参数及参数取值
     */

    public static void onEvent(Context context, String eventId, Map map) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(context, eventId, map);

    }

    /**
     * @param eventId
     * @param label
     * @param map
     */
    public static void onEvent(String eventId, String label, Map map) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(mContext, eventId, label, map);

    }

    /**
     * TD 事件 label,事件参数，事件数值
     *
     * @param eventId
     * @param label
     * @param map
     * @param de
     */
    public static void onEvent(String eventId, String label, Map map, double de) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(mContext, eventId, label, map, de);

    }

    /**
     * TD事件 和label
     *
     * @param eventId
     * @param label
     */
    public static void onEvent(String eventId, String label) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(mContext, eventId, label);

    }

    /**
     * TD 的事件和事件参数
     *
     * @param eventId
     * @param map
     */
    @Keep
    public static void onEvent(String eventId, Map map) {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(mContext, eventId, map);

    }

    /**
     * TD的事件
     *
     * @param eventId
     */
    @Keep
    public static void onEvent(String eventId) {
        if (checkSdkNotInit()) {
            return;
        }

        if (BigFunViewModel.tkdata)
            TalkingDataEvent.WKeeNM(mContext, eventId);

    }


    /**
     * Adjust 收入，去重,事件
     *
     * @param eventId
     */
    @Keep
    public static void TrackEvent(String eventId) {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.adjust)
        AdjustonEvent.TrackEvent(eventId);
    }

    /**
     * 去重
     *
     * @param eventId
     * @param id
     */
    @Keep
    public static void TrackEvent(String eventId, String id) {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.adjust)
        AdjustonEvent.TrackOrderIdEvent(eventId, id);
    }

    /**
     * 收入，去重
     *
     * @param eventId
     * @param hqia
     * @param moey
     * @param id
     */
    @Keep
    public static void TrackEvent(String eventId, double hqia, String moey, String id) {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.adjust)
        AdjustonEvent.TrackRevenueEvent(eventId, hqia, moey, id);
    }

    /**
     * 收入
     *
     * @param eventId
     * @param hqia
     * @param moey
     */
    @Keep
    public static void TrackEvent(String eventId, double hqia, String moey) {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.adjust)
        AdjustonEvent.TrackRevenueEvent(eventId, hqia, moey);
    }


    /**
     * 内购商品的展示
     *
     * @param googleCommodityListener
     */

    public static void googleQueryPay(GoogleCommodityListener googleCommodityListener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!goopay) {
            Log.e("BigFunSDK", "Background not configured");
        }
        MyBillingImpl.googleQueryPay(googleCommodityListener);
    }

    /**
     * 已购买的未消费的商品
     *
     * @param queryPurchaseListener
     */

    public static void googleQueryPurchase(GoogleQueryPurchaseListener queryPurchaseListener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!goopay) {
            Log.e("BigFunSDK", "Background not configured");
        }
        MyBillingImpl.googleQueryPurchase(queryPurchaseListener);
    }

    /**
     * 内购商品的购买，回调确认购买
     *
     * @param activity
     * @param skuDetails
     * @param googleQueryPayListener
     */

    public static void initiatePurchaseFlow(Activity activity, final SkuDetails skuDetails, GoogleQueryPayListener googleQueryPayListener) {
        if (checkSdkNotInit()) {
            return;
        }
        MyBillingImpl.initiatePurchaseFlow(activity, skuDetails, googleQueryPayListener);
    }

    /**
     * 是否真机
     *
     * @return
     */
    public static boolean insad() {
        return EmulatorDetector.with(mContext).detects();
    }

    /**
     * 消费购买的商品
     *
     * @param purchase
     * @param purchaseListener
     */

    public static void consumePurchase(Purchase purchase, GoogleConsumePurchaseListener purchaseListener) {
        if (checkSdkNotInit()) {
            return;
        }
        MyBillingImpl.consumePurchase(purchase, purchaseListener);
    }


    /**
     * 设置是否是Debug模式
     *
     * @param debug
     */

    public static void setDebug(boolean debug) {
        environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        isDebug = debug;
    }

    private static GoogleSignInClient mGoogleSignInClient;

    private static void Googleinit(String clientId) {
//        mGetSignInIntentRequest =
//                GetSignInIntentRequest.builder()
//                        .setServerClientId(clientId)
//                        .build();

        if (mGoogleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                    .DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(clientId)
                    .requestProfile()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        }

    }

    private static Intent getGoogleIntent(){
        return mGoogleSignInClient.getSignInIntent();
    }

    private static void getGoogle(Activity activity) {
            activity.startActivityForResult(getGoogleIntent(),SIGN_GP_LOGIN);
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
        if (!BigFunViewModel.google || mGoogleSignInClient == null) {
            Log.e("BigFunSDK", "Background not set");
            return;
        }
        getGoogle(activity);
    }


//    public static SignInClient BigFunIdentity() {
//        return Identity.getSignInClient(bactivity);
//    }


    /**
     * facebook登录
     *
     * @param context  activity或者fragment的context “必填”
     * @param listener 登录回调
     */

    public static void BigFunLogin(Context context, LoginListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.fblonig) {
            Log.e("BigFunSDK", "Background not configured Facebook");
            return;
        }
        List<String> permissionList = new ArrayList<>();
//        permissionList.add("public_profile");
        permissionList.add("email");

        LoginModel.facebookLogin(context, permissionList, listener);
    }

    /**
     * 退出Facebook登录
     */
    public static void BigFunLogout() {
        if (checkSdkNotInit()) {
            return;
        }
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(mContext);
        if(account!=null)
        mGoogleSignInClient.signOut();
        LoginModel.BigFunLogout();
    }


    /**
     * facebook分享
     *
     * @param context     activity或者fragment的context “必填”
     * @param linkContent 分享类型 “必填”
     * @param listener    分享回调
     */

    public static void BigFunShare(Context context, ShareContent linkContent, ShareListener listener) {
        if (checkSdkNotInit()) {
            return;
        }
        if (!BigFunViewModel.shar) {
            Log.e("BigFunSDK", "Background not configured Facebook");
            return;
        }

        LoginModel.facebookShare(context, linkContent, listener);
    }

    /**
     * @param activity    必填
     * @param textContent 设置文本内容
     */

    public static void BigFunShare(Context activity, String textContent) {
        if (checkSdkNotInit()) {
            return;
        }

        new Share.Builder(activity)
                .setTextContent(textContent)
                .build()
                .shareBySystem();
    }

    public static void BigFunShare(Context activity, Uri shareFileUri) {
        if (checkSdkNotInit()) {
            return;
        }

        new Share.Builder(activity)
                .setShareFileUri(shareFileUri)
                .build()
                .shareBySystem();
    }

    private AdBFPlatForm FBPlatForm;

    /**
     * 插屏
     */


    public static void ShowInterstitialAdLoadAd() {
        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.ISoure) {
            SourceNetWork.showInterstitial();
//            TMNetWork.showInterstitial();
        } else if (BigFunViewModel.TMnet) {
            SourceNetWork.showInterstitial();
//            TMNetWork.showInterstitial();
        } else {
            Log.e("BigFunSDK", "Background not configured ");
            return;
        }

    }

    /**
     * IS奖励视屏
     */

    public static void ShowRewardedVideo(BFRewardedVideoListener listener) {

        if (checkSdkNotInit()) {
            return;
        }
        if(listener==null)
            ShowRewardedVideo();
        else {
            if (BigFunViewModel.ISoure) {
                SourceNetWork.showRewardedVideo(listener);
//            TMNetWork.showRewardedVideo(listener);
            } else if (BigFunViewModel.TMnet) {
                SourceNetWork.showRewardedVideo(listener);
//            TMNetWork.showRewardedVideo(listener);
            } else {
                Log.e("BigFunSDK", "Background not configured ");
                return;
            }
        }
    }

    /**
     * TM奖励视屏回调
     */
//    public static void ShowRewardedVideo(BFTMRewardedVideoListener listener) {
//
//        if (checkSdkNotInit()) {
//            return;
//        }
//        if(listener==null)
////            TMNetWork.showRewardedVideo();
//        if (BigFunViewModel.ISoure) {
////            SourceNetWork.showRewardedVideo(listener);
//            TMNetWork.showRewardedVideo(listener);
//        } else if (BigFunViewModel.TMnet) {
////            SourceNetWork.showRewardedVideo(listener);
//            TMNetWork.showRewardedVideo(listener);
//        } else {
//            Log.e("BigFunSDK", "Background not configured ");
//            return;
//        }
//
//    }

    public static void ShowRewardedVideo() {

        if (checkSdkNotInit()) {
            return;
        }
        if (BigFunViewModel.ISoure) {
            SourceNetWork.showRewardedVideo();
//            TMNetWork.showRewardedVideo();
        } else if (BigFunViewModel.TMnet) {
            SourceNetWork.showRewardedVideo();
//            TMNetWork.showRewardedVideo();
        } else {
            Log.e("BigFunSDK", "Background not configured ");
            return;
        }


    }

    /**
     * 插页，全屏是否可用
     *
     * @return
     */

    @Keep
    public static boolean isInterstitialReady() {
        return SourceNetWork.isInterstitialReady();
//        return TMNetWork.showInterstitial();
    }

    /**
     * 广告视屏是否可以用
     *
     * @return
     */

    @Keep
    public static boolean isRewardedVideoAvailable() {
        return SourceNetWork.isRewardedVideoAvailable();
//        return TMNetWork.showRewardedVideo();
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
        if (BigFunViewModel.ISoure) {
            SourceNetWork.createAndloadBanner(mBannerParentLayout, size);

        } else {
            Log.e("BigFunSDK", "Background not configured");
            return;
        }
    }

    /**
     * 资源释放
     */


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


    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        LoginModel.onActivityResult(requestCode, resultCode, data);
    }

    public static String onResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SIGN_GP_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task == null) {
                LogUtils.log("task：null");
                return "{code:500}";
            }
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                LogUtils.log("Id:" + account.getId() + "|Email:" + account.getEmail() + "|IdToken:" + account.getIdToken());
                return new GoogleBean(account).toString();
            } catch (ApiException e) {
                e.printStackTrace();
                LogUtils.log("ApiException:" + e.getMessage());
                return "{code:" + e.getMessage()+"}";
            }
        }
        return "";
    }

    /**
     * 检查是否初始化
     */
    private static boolean checkSdkNotInit() {
        if (TextUtils.isEmpty(mChannelCode) || mContext == null || !BigFunViewModel.sdk) {
            Log.e("BigFunSDK", "sdk not init");
            talkingDataSDK(BigFunSDK.getTDID());
            return true;
        }
        return false;
    }

}
