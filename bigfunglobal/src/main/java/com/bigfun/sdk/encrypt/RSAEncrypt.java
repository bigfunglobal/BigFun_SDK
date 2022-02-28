package com.bigfun.sdk.encrypt;


import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.gms.common.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSAEncrypt {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String pulicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjHfZczWx3pKpUepg9xh70CroHFTlvdz6to6rDl3w0/e1qUZByh/vB7HVD2wQ32bPks/1l2WDvMcmup50qbR145x9FxZJmCeIVNJLH018mLOUcFfH49TjdoVFJIkzjimboNGCCo/mxdiUehuee/3pYpZ3yOS4l1IBAs5f/mHybCwIDAQAB";
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyXKu/VQdDuRbmDcOBOa4pq3+3dobMB8EFKHNTU4MGLKMBNnV4nRJSlwoziXAgtb2Hz2S83aFicxj/fEKrieIMQEyD/g7tXTR9MN6smyKFnPhkh6TUZG0FgFRjhdj/lwQM3SCV0sttZRtAikOaZiL9vYKq7hDT7xu27tENOSxEiwIDAQAB";
    public static String pivateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjHfZczWx3pKpUepg9xh70CroHFTlvdz6to6rDl3w0/e1qUZByh/vB7HVD2wQ32bPks/1l2WDvMcmup50qbR145x9FxZJmCeIVNJLH018mLOUcFfH49TjdoVFJIkzjimboNGCCo/mxdiUehuee/3pYpZ3yOS4l1IBAs5f/mHybCwIDAQAB";
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALJcq79VB0O5FuYNw4E5rimrf7d2hswHwQUoc1NTgwYsowE2dXidElKXCjOJcCC1vYfPZLzdoWJzGP98QquJ4gxATIP+Du1dNH0w3qybIoWc+GSHpNRkbQWAVGOF2P+XBAzdIJXSy21lG0CKQ5pmIv29gqruENPvG7bu0Q05LESLAgMBAAECgYBnJCPFLtxWk0/klQz7S2mleyEN7KKildFOtEeMZfycs29HokBoERypWeJyTwXiRHZndMziUp+imMBZ7Xd4TvHCALyRsxTPhZsjgWn9cov5uNGYvxZwzhuqV0W5nA/n6Z7MZW6kl00PUHj1rkNv9l2krRxYyV3Tm403+pFohnGo0QJBANhs7YrZ4eUvWOIQx0zbgjamQQdnneBpnYGWWDWOudS57f0oYW4kLrIWSHNB1W4A9IgtAKFnjRfGHYqkOwOP1O0CQQDS+fTHYl3ZhFSaJxUkEA2RO6k7NXIJa72TA3q31Wlf1nY6iIEW618V9uQE8ze5Xk9xA8FPD9ZxTTnniVRyQYhXAkEAq/Fmi5Ds0itan0gbPumKOya8tUhR+q+ODh1G+U3qP6Jz/FPvjBS88ImZh9A+44VowSOXw6PESaYBZFtz8zF7RQJAdAHFOq039Zbx/iFnWoEJNMadc1Ub81ouz1umatcKLgx6zgypDrU1f5pWHvKtvVvCSt5JuPN4cqyIHmxA5uJp2QJAORbLwwlAhVyzS0hVFPmceA7TlpLFfOnKwbgmP59A5coEPkuFjNmVxeKM9IOCwjlwQU45K72ZSAbV06kj5I1okA==";


    public static void main(TextView tv) throws Exception {
        RSAEncrypt rsa = new RSAEncrypt();
        String key = randomNum();//aes 加密数据 随机32位key
        String params = AES2561.encrypt("数据", key);
        String aos = rsa.encrypt(key, getPublicKey(publicKey));// 随机生成key rsa加密
    }

    public static String randomNum() {
        return "";
    }

    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     *
     * @param publicKey 公钥字符
     * @return publicKEY
     * @throws Exception exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     *
     * @param privateKey 私钥字符串
     * @return 私钥对象
     * @throws Exception exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA加密
     *
     * @param content   待加密文本
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception exception
     */
    public static String encrypt(String content, PublicKey publicKey) throws Exception {
        String encode = Base64.encode(content.getBytes());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"RSA/None/PKCS1Padding
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = content.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return new String(Base64.encode(encryptedData));
    }
    /**
     * RSA加密
     *
     * @param content   待加密文本
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception exception
     */
    public static String encrypt(String content, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        // 加载公钥
//        X509EncodedKeySpec datakey = new X509EncodedKeySpec(android.util.Base64.decode(publicKey.getBytes(), android.util.Base64.NO_WRAP));
        X509EncodedKeySpec datakey = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey key = factory.generatePublic(datakey);
//        String encode = Base64.encode(content.getBytes());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"RSA/None/PKCS1Padding
        cipher.init(Cipher.ENCRYPT_MODE, key);
//        content.to
        byte[] data = content.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String encrypt = android.util.Base64.encodeToString(encryptedData, android.util.Base64.DEFAULT);
        return Base64.encode(encryptedData);
    }

    /**
     * RSA解密
     *
     * @param content    密文
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception exception
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String content, String privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(android.util.Base64.decode(privateKey.getBytes(), android.util.Base64.NO_WRAP));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        byte[] encryptedData = Base64.decode(content);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

    /**
     * RSA算法
     */
    public static final String RSA = "RSA";
    /**加密方式，android的*/
// public static final String TRANSFORMATION = "RSA/None/NoPadding";
    /**
     * 加密方式，标准jdk的
     */
    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    /**
     * 使用公钥加密
     */
    public static String encryptByPublicKey(String text, String publicKey) throws Exception {
        // 加载公钥
        X509EncodedKeySpec data = new X509EncodedKeySpec(android.util.Base64.decode(publicKey.getBytes(), android.util.Base64.NO_WRAP));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey key = factory.generatePublic(data);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptData = cipher.doFinal(text.getBytes());
        String encrypt = android.util.Base64.encodeToString(encryptData, android.util.Base64.DEFAULT);
        return encrypt;
    }

    /**
     * 使用私钥解密
     */
    public static String decryptByPrivateKey(String text, String priKey) throws Exception {
        // 加载私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(android.util.Base64.decode(priKey.getBytes(), android.util.Base64.NO_WRAP));
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 解密数据
        Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(text.getBytes("UTF-8"));
        String encrypt = android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
        return new String(arr, "utf-8");
    }

}
