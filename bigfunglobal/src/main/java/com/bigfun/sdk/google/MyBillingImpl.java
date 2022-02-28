package com.bigfun.sdk.google;

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
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MyBillingImpl implements PurchasesUpdatedListener, ConsumeResponseListener {
    private BillingClient billingClient;
    private GooglePayUpdatedListener listener;
    public MyBillingImpl(){

    }

    public void initialize(Context context) {
        billingClient = BillingClient.newBuilder(context).setListener(this).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                // Logic from ServiceConnection.onServiceConnected should be moved here.
                Log.e("onBillingSetupFinished",billingResult.getResponseCode()+"");
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Logic from ServiceConnection.onServiceDisconnected should be moved here.
                Log.e("onBillingServicected","1231313");
            }
        });
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        // 在这里处理从 Billing 库更新购买的回，对 purchases 进行处理
        Log.e("onPurchasesUpdated","11111111111111");
        listener.onPurchasesUpdated(billingResult, list);
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener=new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

        }
    };
    public void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
//        Purchase purchase = ...;

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    @Override
    public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {

    }
    List<String> skuList = new ArrayList<>();
    List<SkuDetails> skuDetailsList=new ArrayList<>();
    // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().

    public void googlepay(Activity activity,String premium_upgrade,GooglePayUpdatedListener googlePayUpdatedListener){
        listener=googlePayUpdatedListener;
        skuList.add(premium_upgrade);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        Log.e("1111","11111");
                    }
                });

// SkuDetails object obtained above.
        SkuDetails skuDetails = null;
        if(skuDetailsList.size()>0)
         skuDetails = skuDetailsList.get(0);


        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        int responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).getResponseCode();

//        BillingFlowParams purchaseParams =
//                BillingFlowParams.newBuilder()
//                        .setSkuDetails(skuDetails)
//                        .build();
//
//        billingClient.launchBillingFlow(activity, purchaseParams);
    }


}

