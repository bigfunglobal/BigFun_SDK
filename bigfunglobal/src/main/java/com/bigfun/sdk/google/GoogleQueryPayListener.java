package com.bigfun.sdk.google;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingResult;

/**
 * 商品，购买回调
 */
public interface GoogleQueryPayListener {
    @Keep
    void onPurchaseResponse(@NonNull BillingResult billingResult);
}
