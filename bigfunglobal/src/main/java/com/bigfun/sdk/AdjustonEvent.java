package com.bigfun.sdk;

import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjustonEvent {


    public static void TrackEvent(String eventId){
        AdjustEvent adjustEvent = new AdjustEvent(eventId);
        Adjust.trackEvent(adjustEvent);
    }
    public static void TrackOrderIdEvent(String eventId,String id){
        AdjustEvent adjustEvent = new AdjustEvent(eventId);
        if(!id.isEmpty())
        adjustEvent.setOrderId(id);
        Adjust.trackEvent(adjustEvent);
    }
    public static void TrackRevenueEvent(String eventId,double hqia,String moey,String id){
        AdjustEvent adjustEvent = new AdjustEvent(eventId);
        adjustEvent.setRevenue(hqia,moey);
        adjustEvent.setOrderId(id);
        Adjust.trackEvent(adjustEvent);
    }
    public static void TrackRevenueEvent(String eventId,double hqia,String moey){
        AdjustEvent adjustEvent = new AdjustEvent(eventId);
        adjustEvent.setRevenue(hqia,moey);
        Adjust.trackEvent(adjustEvent);
    }
}
