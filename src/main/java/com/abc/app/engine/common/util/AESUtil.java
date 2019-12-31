package com.abc.app.engine.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * AES加解密工具
 * 
 * @author 陈勇
 * @date 2019年6月27日
 *
 */
@Slf4j
public class AESUtil {

    public static final int[] KEY_SIZES = { 16 };
    public static final int KEY_SIZE = 16;

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 初始化密钥 {@link #DEFAULT_KEY_SIZE}
     * 
     * @return
     */
    public static String initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 初始化密钥
     * 
     * @param keysize
     * @return
     */
    public static String initKey(int keysize) {
        if (Arrays.stream(KEY_SIZES).noneMatch(item -> item == keysize)) {
            log.error("密钥长度错误！");
            return null;
        }
        return RandomStringUtils.randomAlphanumeric(keysize);
    }

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        return aes(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {
        return aes(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     * 
     * @param content
     * @param password
     * @param mode
     * @return
     */
    private static String aes(String content, String password, int mode) {
        if (content == null || password == null) {
            return null;
        }
        if (Arrays.stream(KEY_SIZES).noneMatch(item -> item == password.length())) {
            log.error("密钥长度错误！");
            return null;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(UTF_8), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(password.substring(0, 16).getBytes(UTF_8));
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, key, iv);
            if (mode == Cipher.ENCRYPT_MODE) {
                byte[] data = cipher.doFinal(content.getBytes(UTF_8));
                return new String(Base64.getEncoder().encode(data), UTF_8);
            } else if (mode == Cipher.DECRYPT_MODE) {
                byte[] data = cipher.doFinal(Base64.getDecoder().decode(content.getBytes(UTF_8)));
                return new String(data, UTF_8);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
