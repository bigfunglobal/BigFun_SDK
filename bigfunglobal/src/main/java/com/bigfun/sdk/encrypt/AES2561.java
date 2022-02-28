package com.bigfun.sdk.encrypt;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES2561 {

    /**
     * 密钥算法 java6支持56位密钥，bouncycastle支持64位
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final byte[] KEY = "/R.m6i*_u7($i,l-@.;P8z5~0+)U+Z=3".getBytes();
    public static final String charset = "UTF-8";

    /**
     * 加密/解密算法/工作模式/填充方式
     * <p>
     * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM =
//            "AES/None/PKCS1Padding";
            "AES/ECB/PKCS7Padding";


    /**
     * 转换密钥
     *
     * @param
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥
        // 生成密钥
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param
     * @param
     * @return String 加密后的数据
     */
    public static String encrypt(String str, String key) throws Exception {
        Key k = toKey(key);
        byte[] data = str.getBytes(charset);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");

        cipher.init(Cipher.ENCRYPT_MODE, k);
        data = cipher.doFinal(data);
        // 执行操作++++++++++++++++
        return new String(Base64.encode(data));
    }

    public static String decryptTest(String str) throws Exception {
        // 欢迎密钥
        Key k = toKey(KEY);
        byte[] data = Base64.decode(str);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        data = cipher.doFinal(data);
        // 执行操作
        return new String(data);
    }

    /**
     * 解密数据
     *
     * @param str 待解密数据
     * @param
     * @return String 解密后的数据
     **/
    public static String decrypt(String str) throws Exception {
        Key k = toKey(KEY);
        byte[] data = android.util.Base64.decode(str, android.util.Base64.NO_WRAP);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        String tt = new String(data);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        data = cipher.doFinal(data);
        // 执行操作
        return new String(data);
    }

    /**
     * 解密数据
     *
     * @param str 待解密数据
     * @param
     * @return String 解密后的数据
     **/
    public static String decrypt(String str, String key) throws Exception {
        Key k = toKey(key.getBytes());
        byte[] data = Base64.decode(str);//Base64.decode(str);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        data = cipher.doFinal(data);
        // 执行操作
        return new String(Base64.decode(new String(data)));//Base64.decodeToString(data);
    }

    public static void main(String[] args) {
        String str = "{\"thirdLogin\":\"false\",\"username\":\"suso\",\"password\":\"aa123456\",\"imei\":\"ZWN0KmZyb21zdHVkZW50d2hlcmVuYQ\",\"channelCode\":\"jx010002\"}";
        String mm = "CiDIGe+wpxuSczf5CnQLKlMhjx4xJuB48DEcbGY2Z9eOZfJqZAGQ61EMZkhXNmnn5ukGobhhfMCQjNqAHVv0a1t0JZGNnoeWaaAgI5PtFLNj1GVZVLhDbLBB3fZSAca3YryY+LiJLmRam4q+7NQG0XotuaJNINGJll1wtm70lzxYJt2z6h6r/zYTgUDx6FtKB7hbHLLwPcrKNPP/6fx8p0bN7uVOxe/XPOqVb8mZ/3dYGSQ6tcKdK34YRsHbPagsebCoPvUCZED/F2oGGyQmF49JimfmB7WcLEuq1KuzV+y6X7RkHxDnH2vInP6PnZ7iPVa0eAS1WZUoysNArpZ+VtJtXTSsT9ru42u8FzUGilZAHSMOVbai1IEENGWQE1OKwDWwZFlAUuMys2mOQwHCggkiQVJAKNmD7HLtBVG0x8bDO0NBtLKDo6wD8Xm0M+gdlyAvHS8u+QDs+9Ked5e0MxeeSXRvKFbLJ/vJIrIn3nGeoem/rAFBmwUPHzP22uwJ/ndKoRNiOtQ/yLMDfT42EA==";
        String s = null;
        String t = null;
        try {
//            s = encrypt(str,null);
            t = decrypt(mm);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s);
        System.out.println(t);

    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(String key) throws Exception {
        // 实例化DES密钥
        // 生成密钥
        if (key != null) {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            return secretKey;
        }
        SecretKey secretKey = new SecretKeySpec(KEY, KEY_ALGORITHM);
        return secretKey;
    }

}
