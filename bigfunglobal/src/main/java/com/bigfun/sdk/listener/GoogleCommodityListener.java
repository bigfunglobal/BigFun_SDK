package com.bigfun.sdk.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;

import java.util.List;

/**
 * 展示内购商品
 */
public interface GoogleCommodityListener {
    void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList);
}
