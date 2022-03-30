package com.bigfun.sdk;





import android.content.Context;

import com.tendcloud.tenddata.TCAgent;

import java.util.Map;

public class TalkingDataEvent {
    /**
     *
     * @param context
     * @param eventId
     */
    public static void WKeeNM(Context context, String eventId,String str,Map map) {
        TCAgent.onEvent(context,eventId,str, map);
    }

    public static void WKeeNM(Context context, String eventId,String str,Map map,double de) {
        TCAgent.onEvent(context,eventId,str, map,de);
    }

    public static void WKeeNM(Context context, String eventId,String str) {
        TCAgent.onEvent(context,eventId,str);
    }

    public static void WKeeNM(Context context,String eventId) {

        TCAgent.onEvent(context,eventId);
    }

    public static void WKeeNM(Context context,String eventId,Map map) {
        TCAgent.onEvent(context,eventId,"",map);
    }
}
