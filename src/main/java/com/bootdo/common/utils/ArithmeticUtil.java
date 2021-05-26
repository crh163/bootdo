package com.bootdo.common.utils;

import com.bootdo.common.constant.CommonConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author rory.chen
 * @date 2021-02-24 13:47
 */
@Slf4j
public class ArithmeticUtil {

    /**
     * AES 加密，并Base64编码
     *
     * @param content
     * @param password
     * @return
     */
    public static String encryptAes(String content, String password) {
        try {
            SecretKey secretKey = buildSecretKey(password);
            Cipher cipher = Cipher.getInstance(CommonConsts.AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("【泰康在线】AES加密失败,{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * AES解密
     *
     * @param content
     * @param password
     * @return
     */
    public static String decryptAes(String content, String password) {
        try {
            SecretKey secretKey = buildSecretKey(password);
            Cipher cipher = Cipher.getInstance(CommonConsts.AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(Base64.decodeBase64(content));
            return new String(encrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("【泰康在线】AES解密失败,{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * build AES key
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey buildSecretKey(String key) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(CommonConsts.AES);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey();
    }


    /**
     * spring的md5加密
     *
     * @param bytes
     * @return
     */
    public static String getMd5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

}
