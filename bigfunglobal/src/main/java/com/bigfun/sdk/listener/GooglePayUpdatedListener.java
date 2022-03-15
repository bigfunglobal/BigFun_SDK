package com.bigfun.sdk.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;

import java.util.List;

public interface GooglePayUpdatedListener {
    void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list);
}
