package com.bigfun.sdk.google;

import static android.content.ContentValues.TAG;


import static com.bigfun.sdk.model.BigFunViewModel.stringins;
import static com.bigfun.sdk.model.BigFunViewModel.stringsus;

import android.app.Activity;
import android.content.Context;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.bigfun.sdk.ExceptionHandler;
import com.bigfun.sdk.listener.GoogleCommodityListener;
import com.bigfun.sdk.listener.GoogleConsumePurchaseListener;
import com.bigfun.sdk.listener.GoogleQueryPayListener;
import com.bigfun.sdk.listener.GoogleQueryPurchaseListener;

import java.util.ArrayList;
import java.util.List;

public class MyBillingImpl {
    private static BillingClient billingClient;

    public MyBillingImpl() {

    }
    private static MyBillingImpl instance;
    //
    public static MyBillingImpl getInstance() {
        if (instance == null) {
            synchronized (MyBillingImpl.class) {
                if (instance == null) {
                    instance = new MyBillingImpl();
                }
            }
        }
        return instance;
    }
    //初始化
    public static void initialize(Context context) {
        ExceptionHandler.install(new ExceptionHandler.CustomExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Log.e("SDK", throwable.getMessage());
            }
        });
        billingClient = BillingClient.newBuilder(context).setListener(purchasesUpdatedListener).enablePendingPurchases().build();
    }

//            int SERVICE_TIMEOUT = -3;//服务超时,在 Google Play 响应之前，请求已达到最大超时。
//            int FEATURE_NOT_SUPPORTED = -2;//当前设备上的 Play 商店不支持请求的功能。
//            int SERVICE_DISCONNECTED = -1;//服务单元已断开,Play 商店服务现在未连接 - 可能是暂时状态。
//            int OK = 0;//成功
//            int USER_CANCELED = 1;//用户按上一步或取消对话框
//            int SERVICE_UNAVAILABLE = 2;//网络连接断开
//            int BILLING_UNAVAILABLE = 3;//所请求的类型不支持 Google Play 结算服务 AIDL 版本
//            int ITEM_UNAVAILABLE = 4;//请求的商品已不再出售。请求的产品不可购买。
//            int DEVELOPER_ERROR = 5;//提供给 API 的参数无效。此错误也可能说明应用未针对结算服务正确签名或设置，或者在其清单中缺少必要的权限。
//            int ERROR = 6;//API 操作期间出现严重错误
//            int ITEM_ALREADY_OWNED = 7;//未能购买，因为已经拥有此商品
//            int ITEM_NOT_OWNED = 8;//未能消费，因为尚未拥有此商品,由于物品不属于自己而未能消费。


    static PurchasesUpdatedListener purchasesUpdatedListener=new PurchasesUpdatedListener() {
        //支付回调
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
            // 在这里处理从 Billing 库更新购买的回，对 purchases 进行处理

            switch (billingResult.getResponseCode()) {
                case BillingClient.BillingResponseCode.OK:
                    if (null != list) {
                        for (Purchase purchase : list) {
                            handlePurchase(purchase);
                        }
                        return;
                    } else {
                        Log.d(TAG, "Null Purchase List Returned from OK response!");
                    }
                    break;
                case BillingClient.BillingResponseCode.USER_CANCELED:
                    Log.i(TAG, "onPurchasesUpdated: User canceled the purchase");
                    break;
                case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                    Log.i(TAG, "onPurchasesUpdated: The user already owns this item");
                    break;
                case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                    Log.e(TAG, "onPurchasesUpdated: Developer error means that Google Play " +
                            "does not recognize the configuration. If you are just getting started, " +
                            "make sure you have configured the application correctly in the " +
                            "Google Play Console. The SKU product ID must match and the APK you " +
                            "are using must be signed with release keys."
                    );
                    break;
                default:
                    Log.d(TAG, "BillingResult [" + billingResult.getResponseCode() + "]: "
                            + billingResult.getDebugMessage());
            }

        }
    };


    //确认购买完成
    static AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            //调用以通知确认购买操作已完成。
            googleQueryPayListener.onPurchaseResponse(billingResult);
        }
    };

    //商品成功后消费流程
    public static void handlePurchase(Purchase purchase) {

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }

        }
    }


    //商品消费成功
    static ConsumeResponseListener listener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
            purchaseListener.onConsumePurchase(billingResult, purchaseToken);

        }
    };
    private static GoogleConsumePurchaseListener purchaseListener;

    //商品消耗掉，才能从新购买该商品
    public static void consumePurchase(Purchase purchase, GoogleConsumePurchaseListener purchasesListener) {
        purchaseListener = purchasesListener;
        //核实购买情况。
        //确保尚未为此purchaseToken授予权限。
        //授予用户权限。
        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        billingClient.consumeAsync(consumeParams, listener);
    }

    /**
     * 查询最近的购买交易
     * 使用 Google Play 商店应用的缓存，而不发起网络请求
     * 建议在应用启动时和 onResume() 方法中调用 至少调用两次
     */
    private static List<Purchase> purchaseList = new ArrayList<>();

    public static void queryPurchase(GoogleQueryPurchaseListener queryPurchaseListener) {

        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, new PurchasesResponseListener() {
            @Override
            public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                queryPurchaseListener.onQueryPurchasesResponse(billingResult, list);
            }
        });

    }
    //执行成功之后再次处理消费流程handlePurchase(purchase, false);


    //自定义内购商品展示页，商品回调
    public static void googleQueryPay(GoogleCommodityListener googleCommodityListener) {

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                // Logic from ServiceConnection.onServiceConnected should be moved here.
                pay(googleCommodityListener);
            }

            @Override
            public void onBillingServiceDisconnected() {
                // 来自ServiceConnection的逻辑。onServiceDisconnected应该移到这里。

            }
        });
    }

    //自定义购买商品查询
    public static void googleQueryPurchase(GoogleQueryPurchaseListener queryPurchaseListener) {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                // Logic from ServiceConnection.onServiceConnected should be moved here.

                queryPurchase(queryPurchaseListener);
            }

            @Override
            public void onBillingServiceDisconnected() {
                // 来自ServiceConnection的逻辑。onServiceDisconnected应该移到这里。

            }
        });
    }


    private static GoogleQueryPayListener googleQueryPayListener;
    private static GoogleCommodityListener googleCommodityListener;
    static List<String> skuList = new ArrayList<>();
    static List<String> skuLists = new ArrayList<>();
    private static boolean inapp = true;
    private static boolean subs = true;

    //查询内购商品
    public static void pay(GoogleCommodityListener googlepayCommodityListener) {

        googleCommodityListener = googlepayCommodityListener;
        skuLists=stringsus;
        skuList=stringins;
        if (!skudetails) {
            skuDetails.clear();
            if (null != skuList && !skuList.isEmpty()) {
                inapp = false;
                billingClient.querySkuDetailsAsync(SkuDetailsParams.newBuilder()
                        .setType(BillingClient.SkuType.INAPP)
                        .setSkusList(skuList)
                        .build(), skuDetailsResponseListener);
            }
            if (null != skuLists && !skuLists.isEmpty()) {
                subs = false;
                billingClient.querySkuDetailsAsync(SkuDetailsParams.newBuilder()
                        .setType(BillingClient.SkuType.SUBS)
                        .setSkusList(skuLists)
                        .build(), skuDetailsResponseListener);
            }
        }else {
            googleCommodityListener.onSkuDetailsResponse(result, skuDetails);
        }
    }


    //内购商品的购买
    public static void initiatePurchaseFlow(Activity activity, final SkuDetails skuDetails, GoogleQueryPayListener googlequeryPayListener) {
        googleQueryPayListener = googlequeryPayListener;
        BillingFlowParams purchaseParams = BillingFlowParams.newBuilder().
                setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(activity, purchaseParams);

    }

    private static List<SkuDetails> skuDetailsinapp = new ArrayList<>();
    private static List<SkuDetails> skuDetailssubs = new ArrayList<>();
    private static List<SkuDetails> skuDetails = new ArrayList<>();
    private static boolean skudetails = false;
    private static BillingResult result;

    static SkuDetailsResponseListener skuDetailsResponseListener=new SkuDetailsResponseListener() {
        @Override
        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList) {

            // Process the result.

            int responseCode = billingResult.getResponseCode();
            String debugMessage = billingResult.getDebugMessage();
            switch (responseCode) {
                case BillingClient.BillingResponseCode.OK:
                    Log.i(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                    if (skuDetailsList == null || skuDetailsList.isEmpty()) {
                        Log.e(TAG, "onSkuDetailsResponse: " +
                                "Found null or empty SkuDetails. " +
                                "Check to see if the SKUs you requested are correctly published " +
                                "in the Google Play Console.");
                    } else {

                        skuDetailsinapp.clear();
                        skuDetailssubs.clear();
                        for (int i = 0; i < skuDetailsList.size(); i++) {
                            if (skuDetailsList.get(i).getType().equals(BillingClient.SkuType.INAPP)) {
                                inapp = true;
                                skuDetailsinapp.add(skuDetailsList.get(i));
                            }
                            if (skuDetailsList.get(i).getType().equals(BillingClient.SkuType.SUBS)) {
                                subs = true;
                                skuDetailssubs.add(skuDetailsList.get(i));
                            }
//
                        }
                        skuDetails.addAll(skuDetailsinapp);
                        skuDetails.addAll(skuDetailssubs);
                        if (inapp && subs) {
                            result=billingResult;
                            googleCommodityListener.onSkuDetailsResponse(billingResult, skuDetails);
                            skudetails = true;
                        }
                    }
                    break;
                case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                case BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE:
                case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                case BillingClient.BillingResponseCode.ERROR:
                    Log.e(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                    break;
                case BillingClient.BillingResponseCode.USER_CANCELED:
                    Log.i(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                    break;
                // These response codes are not expected.
                case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                case BillingClient.BillingResponseCode.ITEM_NOT_OWNED:
                default:
                    Log.wtf(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
            }

        }
    };

}

