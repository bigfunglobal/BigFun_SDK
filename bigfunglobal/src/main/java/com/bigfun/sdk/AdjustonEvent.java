package com.bigfun.sdk;

import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjustonEvent {

    public static void TrackEvent(String eventId, Map map){
        AdjustEvent adjustEvent = new AdjustEvent(eventId);
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            adjustEvent.addCallbackParameter(key,map.get(key)+"");
        }
        Adjust.trackEvent(adjustEvent);
    }

}
