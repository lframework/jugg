package com.lframework.starter.web.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.lframework.starter.web.config.properties.SecretProperties;

/**
 * 加密工具类
 * 提供字符串加密解密功能，基于AES算法进行数据加密
 * 包括字符串加密、解密、密钥生成等功能
 *
 * @author lframework@163.com
 */
public class EncryptUtil {

    /**
     * 字符串加密（使用默认配置）
     * 使用应用配置中的密钥进行AES加密
     *
     * @param s 要加密的字符串，不能为null
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(String s) {
        SecretProperties properties = ApplicationUtil.getBean(SecretProperties.class);

        return encrypt(s, properties);
    }

    /**
     * 字符串加密（指定配置）
     * 使用指定的密钥配置进行AES加密
     *
     * @param s 要加密的字符串，不能为null
     * @param properties 密钥配置，不能为null
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(String s, SecretProperties properties) {
        byte[] key = Base64.decode(properties.getKey());
        AES aes = SecureUtil.aes(key);

        return aes.encryptHex(s);
    }

    /**
     * 字符串解密（使用默认配置）
     * 使用应用配置中的密钥进行AES解密
     *
     * @param s 要解密的十六进制字符串，不能为null
     * @return 解密后的原始字符串
     */
    public static String decrypt(String s) {
        SecretProperties properties = ApplicationUtil.getBean(SecretProperties.class);

        return decrypt(s, properties);
    }

    /**
     * 字符串解密（指定配置）
     * 使用指定的密钥配置进行AES解密
     *
     * @param s 要解密的十六进制字符串，不能为null
     * @param properties 密钥配置，不能为null
     * @return 解密后的原始字符串
     */
    public static String decrypt(String s, SecretProperties properties) {
        byte[] key = Base64.decode(properties.getKey());
        AES aes = SecureUtil.aes(key);

        return aes.decryptStr(s);
    }

    /**
     * 生成AES密钥
     * 生成Base64编码的AES密钥
     *
     * @return Base64编码的AES密钥字符串
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
