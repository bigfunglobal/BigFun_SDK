package com.bigfun.sdk.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static String getIp(Context context) {
        String ip = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isAvailable()) {
                    NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (mobile != null && wifi != null) {
                        if (mobile.isConnected()) {
                            ip = getLocalIp();
                        } else if (wifi.isConnected()) {
                            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            ip = getWifiIp(wm.getConnectionInfo().getIpAddress());
                        }
                    }
                }
            }
        }
        return ip;
    }

    /**
     * 移动网络下获取ip
     */
    public static String getLocalIp() {
        String ipv4 = "";
        List<NetworkInterface> list = null;
        try {
            list = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : list) {
                ArrayList<InetAddress> childList = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : childList) {
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ipv4 = inetAddress.getHostAddress();
                        return ipv4;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipv4;
    }

    /**
     * 获取wifi状态下的ip地址
     */
    private static String getWifiIp(int ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip & 0xFF).append(".");
        sb.append(ip >> 8 & 0xFF).append(".");
        sb.append(ip >> 16 & 0xFF).append(".");
        sb.append(ip >> 24 & 0xFF);
        return sb.toString();
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            name = "1.0.0";
        }
        return name;
    }

    /**
     * 判断是否安装应用
     *
     * @param context
     * @param appPackageName
     * @return
     */
    public static boolean isInstall(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> info = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (info != null) {
            for (int i = 0; i < info.size(); i++) {
                String pn = info.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取某个应用的版本名
     *
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (info.packageName.equals("net.one97.paytm")) {
                return info.versionName;
            }
        }
        return "";
    }

    public static int versionCompare(String str1, String str2) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            return 1;
        }
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        while (i < vals1.length && i < vals2.length && vals1[i].equalsIgnoreCase(vals2[i])) {
            i++;
        }
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        return Integer.signum(vals1.length - vals2.length);
    }
    /**
     * 判断当前网络是否可用(6.0以上版本)
     * 实时
     * @param context
     * @return
     */
    public static boolean isNetSystemUsable(Context context) {
        boolean isNetUsable = false;
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities =
                    manager.getNetworkCapabilities(manager.getActiveNetwork());
            if (networkCapabilities != null) {
                isNetUsable = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }
        }
        return isNetUsable;
    }
//    public static boolean isOnline(){
//        URL url;
//        try {
//            url = new URL("https://www.baidu.com");
//            InputStream stream = url.openStream();
//            return true;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public static boolean netWorkIsEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            // 通过ping百度检测网络是否可用
            Process p = Runtime.getRuntime().exec("/system/bin/ping -c " + 1 + " 202.108.22.5");
            int status = p.waitFor(); // 只有0时表示正常返回
            return (connectivityManager.getActiveNetworkInfo() != null && status == 0);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }
}
