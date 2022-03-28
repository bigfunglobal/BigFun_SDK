package com.bigfun.sdk.utils;

import android.content.Context;
import android.os.StrictMode;

import com.bigfun.sdk.utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpUtils {
    private static String[] platforms = {
//            "http://ip-api.com/json/?fields=66846719"
//            ,
//            "http://pv.sohu.com/cityjson?ie=utf-8"
            "http://ipwhois.app/json/"
    };

    public static String getOutNetIP(Context context,String s) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(platforms[0]+s);
                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setConnectTimeout(5 * 1000);//设置从主机读取数据超时
//                urlConnection.setReadTimeout(5 * 1000);// 设置是否使用缓存  默认是true
                urlConnection.setUseCaches(true);// 设置为Post请求
                urlConnection.setRequestMethod("GET");//urlConn设置请求头信息
                urlConnection.setRequestProperty("Content-Type", "application/json");
                //设置客户端与服务连接类型
                urlConnection.addRequestProperty("Connection", "Keep-Alive");
                urlConnection.connect();            // 开始连接

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                        return builder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return "";
    }
}
