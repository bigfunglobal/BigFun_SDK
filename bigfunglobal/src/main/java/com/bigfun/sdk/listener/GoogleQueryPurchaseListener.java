package com.bigfun.sdk.listener;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;

import java.util.List;

/**
 * 查询购买的商品，一次商品
 */
public interface GoogleQueryPurchaseListener {
    void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list);
}
