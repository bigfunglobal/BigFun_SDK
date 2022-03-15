package com.bigfun.sdk.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingResult;

/**
 * 商品，购买回调
 */
public interface GoogleQueryPayListener {

    void onPurchaseResponse(@NonNull BillingResult billingResult);
}
