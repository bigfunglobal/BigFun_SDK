package com.bigfun.sdk.utils;

import android.util.Log;

import com.bigfun.sdk.type.AdBFPlatForm;

import java.lang.reflect.Array;

public class Distribution_es {

    public static AdBFPlatForm RandomMooncake(int fb,int tm){
        int first = (int)(0+Math.random()*100);
        Log.e("RandomMooncake23123",first+"");
        AdBFPlatForm type;
//        if((first>=0)&&(first<=fb)){
            type =AdBFPlatForm.Facebook;
//        }else {
//            type =AdBFPlatForm.TigerMedia;
//        }
        return type;
    }

}
