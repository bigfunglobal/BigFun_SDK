package com.bigfun.sdk.utils;

import android.util.Log;

import com.bigfun.sdk.type.AdBFPlatForm;

import java.lang.reflect.Array;

public class Distribution_es {

    public static AdBFPlatForm RandomMooncake(int fb,int tm){
        int first = (int)(0+Math.random()*100);
        AdBFPlatForm type;
//        if((first>=0)&&(first<=fb)){
            type =AdBFPlatForm.Facebook;
//        }else {
//            type =AdBFPlatForm.TigerMedia;
//        }
        return type;
    }


    public static String adadawco(String sadad){
        String sda="";
        Log.e("kfoasd",sadad.contains("country:")+"");
        if(sadad.contains("country:")) {
            String hgda = sadad.replace("country:", "");
            String[] da = hgda.split(",");
            for (int i = 0; i < da.length; i++) {
                    if (da[i].contains("{")) {
                        int sa = da[i].indexOf("{");
                        if(i==0)
                        sda = da[i].substring(0, sa );
                        else
                            sda +="," +da[i].substring(0, sa );
                    } else {
                        if(i==0)
                        sda = da[i];
                        else
                            sda+=","+da[i];
                    }
            }
        }
        return sda;
    }
    public static String asdaod(String asda){
        String hjwkq="";
        if(asda.contains("country:")) {
            String hgda = asda.replace("country:", "");
            String[] da = hgda.split(",");
            for (int i = 0; i < da.length; i++) {
                if (da[i].contains("{")) {
                    int sa = da[i].indexOf("{");
                    int sas = da[i].indexOf("}");
                    hjwkq=da[i].substring(sa+1,sas);
                    hjwkq=hjwkq.replace("Province:","");
                }
            }
        }
        return hjwkq;
    }
}
