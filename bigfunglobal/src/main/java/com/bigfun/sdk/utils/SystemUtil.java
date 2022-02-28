package com.bigfun.sdk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.bigfun.sdk.BigFunSDK;

import java.io.FileReader;
import java.util.Locale;

@SuppressLint("MissingPermission")
public class SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }


    private static SystemUtil instance;

    private Context act;
    private WindowManager windowManager;
    private DisplayMetrics dm;
    private ConnectivityManager cm;
    private Display display;

    private SystemUtil(Context act) {
        windowManager=(WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        cm= (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        display=windowManager.getDefaultDisplay();
        dm=act.getResources().getDisplayMetrics();
        this.act = act;
    }

    public static SystemUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SystemUtil.class) {
                if (instance == null) {
                    instance = new SystemUtil(context);
                }
            }
        }
        return instance;
    }
//    /** 是否处于飞行模式 */
//    public boolean isAirModeOpen() {
//        return (Settings.System.getInt(act.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true
//                : false);
//    }
//    /** 获取手机号码 */
//
//    public String getPhoneNumber() {
//        return tm == null ? null : tm.getLine1Number();
//    }
    /** 获取网络类型（暂时用不到） */
    public String getNetWorkType() {

        return cm.getActiveNetworkInfo() == null ? null : cm.getActiveNetworkInfo().getTypeName();
    }
//    /** 获取手机sim卡的序列号（IMSI） */
//    public String getIMSI() {
//        return tm == null ? null : tm.getSubscriberId();
//    }
//    /** 获取手机IMEI */
//    public String getIMEI() {
//        return tm == null ? null : tm.getDeviceId();
//    }
    /** 获取手机型号 */
    public static String getModel() {
        return android.os.Build.MODEL;
    }
    /** 获取手机品牌 */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
    /** 获取手机系统版本 */
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /** 获取手机屏幕宽 */
    public int getScreenWidth() {
        return dm.widthPixels;
    }
    /** 获取手机屏高宽 */
    public int getScreenHeight() {
        return dm.heightPixels;
    }
    /** 获取手机屏高宽 */
    public String getResolution() {
        return dm.widthPixels+"x"+dm.heightPixels;
    }
    /** 获取应用包名 */
    public String getPackageName() {
        return act.getPackageName();
    }
    /**
     * 获取手机MAC地址 只有手机开启wifi才能获取到mac地址
     */
    public String getMacAddress() {
        String result = "";
        WifiManager wifiManager = (WifiManager) act.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }

    /** 获取Application中的meta-data内容 */
    public String getMetaData(String name) {
        String result = "";
        try {
            ApplicationInfo appInfo = act.getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            result = appInfo.metaData.getString(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
