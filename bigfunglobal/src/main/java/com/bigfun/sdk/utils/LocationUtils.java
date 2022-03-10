package com.bigfun.sdk.utils;

import static com.bigfun.sdk.HttpUtils.REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.bigfun.sdk.BigFunSDK;
import com.bigfun.sdk.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {
    // 纬度
    public static double latitude = 0.0;
    // 经度
    public static double longitude = 0.0;
    private static Context context;
    private static LocationUtils instance;
    private static boolean per =false;
    private static LocationManager locationManager;
    public static LocationUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SystemUtil.class) {
                if (instance == null) {
                    instance = new LocationUtils(context);
                }
            }
        }
        return instance;
    }
    private LocationUtils(Context context){
        this.context=context;
        requestStoragePermission();
    }
    private static String[] PERMISSION_LIST=new String[]{
        Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static void requestStoragePermission() {
        List<String> needRequestList = checkPermission(context, PERMISSION_LIST);
        if (needRequestList.isEmpty()) {
            //已授权
            per=true;
        } else {
            //申请权限
            per=false;
            return;
//            requestPermission(needRequestList);
        }
    }
    private static List<String> checkPermission(Context context, String[] checkList) {
        List<String> list = new ArrayList<>();
        for (String s : checkList) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat
                    .checkSelfPermission(context, s)) {
                list.add(s);
            }
        }
        return list;
    }

    private static void requestPermission(List<String> needRequestList) {
        ActivityCompat
                .requestPermissions((Activity) context, needRequestList.toArray(new String[0]),
                        REQUEST_CODE);
    }
    /**
     * 初始化位置信息
     *
     */
    @SuppressLint("MissingPermission")
    public static String initLocation() {
        if(!per){
            return "";
        }
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LogUtils.log(location+"");
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                return location.getProvider()+","+location.getTime()+","+location.getLongitude()+","+location.getLatitude();
            }
        } if(locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {

                    }
                }
            };
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 0, locationListener, Looper.myLooper());
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LogUtils.log(location+"");
            if (location != null) {
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
                locationManager.removeUpdates(locationListener);
                return location.getProvider()+","+location.getTime()+","+location.getLongitude()+","+location.getLatitude();
            }
        }
        return "";
    }

}
