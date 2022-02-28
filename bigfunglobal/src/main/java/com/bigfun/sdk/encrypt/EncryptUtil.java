package com.bigfun.sdk.encrypt;


import static com.bigfun.sdk.encrypt.RSAEncrypt.getPrivateKey;
import static com.bigfun.sdk.encrypt.RSAEncrypt.getPublicKey;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by admin on 2018/4/3.
 */

public class EncryptUtil {
    static String key = "KSFTP622WMTZMFDFJ8O7RVQKXHMXA2";

    // 加密开关   默认false  不加密
    public static boolean isEncrypt = true;
    // 解密开关 同上
    public static boolean isDecrypt = true;

    /**
     * @param data 待加密数据
     */
    public static String encrypt(String data) {
        String encrypt = "";
        try {
            if (isEncrypt == true) {
                encrypt = encryptData(data);
            } else {
                encrypt = data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypt;
    }


    public static Map<String, Object> encrypt(Map<String, Object> mapData, String jsonData) {
        Map<String, Object> encryptMap = new HashMap<>();
        try {
            if (isEncrypt == true) {
                Map<String, Object> map = encryptMapData(jsonData);
                encryptMap = map;
            } else {
                encryptMap = mapData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptMap;
    }

    /**
     * 解密1
     */
    public static String decrypt(String data) {
        String decrypt = "";
        try {
            if (isDecrypt == true) {
                decrypt = AES2561.decrypt(data);
            } else {
                decrypt = data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    /**
     * 解密2
     */
    public static String decryptData(String data) {
        String s = data;
        String decrypt = "";
        try {
            if (isDecrypt == true) {
                decrypt = AES2561.decrypt(s);
            } else {
                decrypt = s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }


    /**
     * 随机生成32位字母
     */
    public static String random32() {
        String str = "";
        for (int i = 0; i < 32; i++) {
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }

    public static String sin(TreeMap<String,Object> treeMap){

        StringBuffer stringBuffer=new StringBuffer();
        Iterator<Map.Entry<String, Object>> it = treeMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String result=stringBuffer.substring(0,stringBuffer.length()-1);
        return result;
    }


    // 数据加密
    public static String encryptData(String data) throws Exception {
        String str32 = random32();
        RSAEncrypt rsa = new RSAEncrypt();
        String params = TestAESUtil.encrypt(data, str32);
        String aos = rsa.encrypt(str32, getPublicKey(rsa.pulicKey));// key加密
        Map<String, String> map = new HashMap<>();
        map.put("aos", aos);
        map.put("params", params);
        return new Gson().toJson(map);
    }

    // 验证码数据加密
    public static String encryptSMSData(TreeMap<String,Object> treeMap) throws Exception {
        String str32 = sin(treeMap);
        String sign =MD5Utils.getMD5Standard(str32).toLowerCase();
        TreeMap<String,Object> map = new TreeMap<>();
        map.putAll(treeMap);
        map.put("sign",sign);
        return new Gson().toJson(map);
    }

    // SDK获取参数数据加密
    public static String encryptsdkData(String data) throws Exception {
        RSAEncrypt rsa = new RSAEncrypt();
        String aos = rsa.encryptByPublicKey(data, rsa.publicKey);// key加密
        Map<String, String> map = new HashMap<>();
        map.put("requestBody", aos);

        return new Gson().toJson(map);
    }
    public static String encryptsdkreData(String data) throws Exception {
        RSAEncrypt rsa = new RSAEncrypt();
        String aos = rsa.encrypt(data, getPublicKey(rsa.publicKey));// key加密
        Map<String, String> map = new HashMap<>();
        map.put("requestBody", aos);

        return new Gson().toJson(map);
    }
    // SDK获取参数数据解密

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decryptDataSDK(String data) throws Exception {
        RSAEncrypt rsa = new RSAEncrypt();
        String aos = rsa.decrypt(data, rsa.privateKey);// key加密
        return aos;
    }


    // 数据加密
    private static Map<String, String> encryptHttpMapData(String data) throws Exception {
        String str32 = random32();
        RSAEncrypt rsa = new RSAEncrypt();
        String params = AES2561.encrypt(data, str32);//数据加密
        String aos = rsa.encrypt(str32, getPublicKey(rsa.publicKey));// key加密
        Map<String, String> map = new HashMap<>();
        map.put("aos", aos);
        map.put("params", params);
        return map;
    }

    // 数据加密
    private static Map<String, Object> encryptMapData(String data) throws Exception {
        String str32 = random32();
        RSAEncrypt rsa = new RSAEncrypt();
        String params = AES2561.encrypt(str32, data);//数据加密
        Log.i("parms2==", params);

        String aos = rsa.encrypt(str32, getPublicKey(rsa.publicKey));// key加密
        Map<String, Object> map = new HashMap<>();
        map.put("aos", aos);
        map.put("params", params);
        return map;
    }
}

