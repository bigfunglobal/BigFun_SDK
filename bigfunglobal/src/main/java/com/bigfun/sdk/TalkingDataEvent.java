package com.bigfun.sdk;





import android.content.Context;

import com.tendcloud.tenddata.TalkingDataGA;

import java.util.HashMap;
import java.util.Map;

public class TalkingDataEvent {
    /**
     *
     * @param context
     * @param eventId
     */
    public static void WKeeNM(Context context, String eventId,Map map) {

        TalkingDataGA.onEvent(context,eventId, map);
    }
    public static void WKeeNM(String eventId) {

        TalkingDataGA.onEvent(eventId);
    }

    public static void WKeeNM(String eventId,Map map) {

        TalkingDataGA.onEvent(eventId,map);
    }
}
