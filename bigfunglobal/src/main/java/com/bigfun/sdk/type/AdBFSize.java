package com.bigfun.sdk.type;

import androidx.annotation.StringDef;

import com.facebook.ads.Ad;
import com.facebook.ads.AdSize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//public enum AdBFSize {
//    BANNER_HEIGHT_50,BANNER_HEIGHT_90,RECTANGLE_HEIGHT_250
//}

@Retention(RetentionPolicy.CLASS)
public @interface AdBFSize{

    final AdSize BANNER_HEIGHT_50=AdSize.BANNER_HEIGHT_50;

    final AdSize BANNER_HEIGHT_90=AdSize.BANNER_HEIGHT_90;

    final AdSize RECTANGLE_HEIGHT_250=AdSize.RECTANGLE_HEIGHT_250;
}
