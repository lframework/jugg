package com.lframework.starter.web.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.lframework.starter.web.config.properties.SecretProperties;

public class EncryptUtil {

    /**
     * 字符串加密
     *
     * @param s
     * @return
     */
    public static String encrypt(String s) {
        SecretProperties properties = ApplicationUtil.getBean(SecretProperties.class);

        return encrypt(s, properties);
    }

    /**
     * 字符串加密
     *
     * @param s
     * @param properties
     * @return
     */
    public static String encrypt(String s, SecretProperties properties) {
        byte[] key = Base64.decode(properties.getKey());
        AES aes = SecureUtil.aes(key);

        return aes.encryptHex(s);
    }

    /**
     * 字符串解密
     *
     * @param s
     * @return
     */
    public static String decrypt(String s) {
        SecretProperties properties = ApplicationUtil.getBean(SecretProperties.class);

        return decrypt(s, properties);
    }

    /**
     * 字符串解密
     *
     * @param s
     * @param properties
     * @return
     */
    public static String decrypt(String s, SecretProperties properties) {
        byte[] key = Base64.decode(properties.getKey());
        AES aes = SecureUtil.aes(key);

        return aes.decryptStr(s);
    }

    /**
     * 生成秘钥
     *
     * @return
     */
    public static String generateKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.encode(key);
    }

    /*public static void main(String[] args) {
        String s = "需要加密的明文密码";
        byte[] key = Base64.decode("秘钥，见配置文件jugg.secret.key");
        AES aes = SecureUtil.aes(key);

        System.out.println(aes.encryptHex(s));
    }*/
}
