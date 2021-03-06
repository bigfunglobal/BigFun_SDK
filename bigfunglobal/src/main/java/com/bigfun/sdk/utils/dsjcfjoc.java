package com.bigfun.sdk.utils;

import static com.bigfun.sdk.BigFunSDK.TAG;
import static com.bigfun.sdk.BigFunSDK.mContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

public class dsjcfjoc {

    // wifi下获取本地网络IP地址（局域网地址）
    public static String getLocalIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
            return ipAddress;
        }
        return "";
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
    // 获取有限网IP
    public static String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {

        }
        return "0.0.0.0";

    }
    /**
     * 获取外网ip地址（非本地局域网地址）的方法
     */
    public static String getOutNetIP() {
        String ipAddress = "";
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String address = "http://pv.sohu.com/cityjson?ie=utf-8";
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36"); //设置浏览器ua 保证不出现503

            //设置客户端与服务连接类型
//            connection.addRequestProperty("Connection", "Keep-Alive");
//            connection.connect();            // 开始连接

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in,"utf-8"));

                String tmpString;
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString);
                }
                reader.close();//内部会关闭 InputStream
                connection.disconnect();

                //截取字符串
                int satrtIndex = retJSON.indexOf("{");//包含[
                int endIndex = retJSON.indexOf("}");//包含]
                String json = retJSON.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                JSONObject jo = new JSONObject(json);
                String ip = jo.getString("cip");
                return ip;
            } else {
                Log.e(TAG, "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            Log.e(TAG, "获取IP地址时出现异常，异常信息是：" + e.toString());
            ipAddress=getLocalIPAddress(mContext);
        }
        return ipAddress;
    }
    @SuppressLint("MissingPermission")
    public static String getIpAddress(Context context) {
        if (context == null) {
            return "";
        }

        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo info = conManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 3/4g网络
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return getHostIp();
                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
//                    return getLocalIPAddress(context); // 局域网地址
                    return getOutNetIP(); // 外网地址
                } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    // 以太网有限网络
                    return getHostIp();
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";

    }

}
