package com.bigfun.sdk;

import androidx.annotation.Keep;

import com.kochava.base.Tracker;

@Keep
public class BigFunEvent {

    /**
     * 分享
     */
    public static final String EVENT_TYPE_SHARE = "EVENT_TYPE_SHARE";
    /**
     * 充值
     */
    public static final String EVENT_TYPE_PURCHASE = "EVENT_TYPE_PURCHASE";
    /**
     * 提现
     */
    public static final String EVENT_TYPE_WITHDRAW = "EVENT_TYPE_WITHDRAW";
    /**
     * 进入房间
     */
    public static final String EVENT_TYPE_ENTER_ROOM = "EVENT_TYPE_ENTER_ROOM";
    /**
     * 手机登录
     */
    public static final String EVENT_TYPE_PHONE_LOGIN = "EVENT_TYPE_PHONE_LOGIN";
    /**
     * facebook登录
     */
    public static final String EVENT_TYPE_FACEBOOK_LOGIN = "EVENT_TYPE_FACEBOOK_LOGIN";
    /**
     * google登录
     */
    public static final String EVENT_TYPE_GOOGLE_LOGIN = "EVENT_TYPE_GOOGLE_LOGIN";
    /**
     * 游客登录
     */
    public static final String EVENT_TYPE_TOURIST_LOGIN = "EVENT_TYPE_TOURIST_LOGIN";

    private static class InstanceHolder {
        private static BigFunEvent instance = new BigFunEvent();
    }

    public static BigFunEvent getInstance() {
        return InstanceHolder.instance;
    }

    @Keep
    public void sendEvent(String eventType) {
        Tracker.sendEvent(new Tracker.Event(eventType));
    }

    @Keep
    public void sendEvent(String eventType, String params) {
        Tracker.sendEvent(eventType, params);
    }
}
