package com.bigfun.sdk;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * FirebaseAnalytics数据埋点
 */

public class FirebaseEvent {

    public static void TrackEvent(Context context,String eventId, Map map) {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putLong("time",System.currentTimeMillis());
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            bundle.putString(key,map.get(key)+"");
        }
        mFirebaseAnalytics.logEvent(eventId, bundle);
    }
}
