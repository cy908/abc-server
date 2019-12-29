package com.abc.app.engine.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;

/**
 * RSA加解密工具
 * 
 * @author 陈勇
 * @date 2019年10月29日
 * 
 */
@Slf4j
public class RSAUtil {

    public static final int KEY_SIZE = 2048;
    public static final String PUBLIC_KEY = "PublicKey";
    public static final String PRIVATE_KEY = "PrivateKey";

    private static final String ALGORITHM = "RSA";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 初始化密码 {@link #KEY_SIZE}
     * 
     * @return
     */
    public static Map<String, Key> initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 初始化密码
     * 
     * @param keysize
     * @return
     */
    public static Map<String, Key> initKey(int keysize) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGen.initialize(keysize);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Key> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取公钥
     * 
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Key> keyMap) {
        if (keyMap == null) {
            return null;
        }
        Key key = keyMap.get(PUBLIC_KEY);
        if (key == null) {
            return null;
        }
        return new String(Base64.getEncoder().encode(key.getEncoded()), UTF_8);
    }

    /**
     * 获取私钥
     * 
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Key> keyMap) {
        if (keyMap == null) {
            return null;
        }
        Key key = keyMap.get(PRIVATE_KEY);
        if (key == null) {
            return null;
        }
        return new String(Base64.getEncoder().encode(key.getEncoded()), UTF_8);
    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String encryptByPublicKey(String data, String key) {
        return rsa(data, key, Cipher.ENCRYPT_MODE, Cipher.PUBLIC_KEY);
    }

    /**
     * 公钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String decryptByPublicKey(String data, String key) {
        return rsa(data, key, Cipher.DECRYPT_MODE, Cipher.PUBLIC_KEY);
    }

    /**
     * 私钥加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String encryptByPrivateKey(String data, String key) {
        return rsa(data, key, Cipher.ENCRYPT_MODE, Cipher.PRIVATE_KEY);
    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String decryptByPrivateKey(String data, String key) {
        return rsa(data, key, Cipher.DECRYPT_MODE, Cipher.PRIVATE_KEY);
    }

    private static String rsa(String content, String password, int mode, int type) {
        if (content == null || password == null) {
            return null;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(password);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            Key key = null;
            if (type == Cipher.PUBLIC_KEY) {
                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
                key = keyFactory.generatePublic(x509KeySpec);
            } else if (type == Cipher.PRIVATE_KEY) {
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
                key = keyFactory.generatePrivate(pkcs8KeySpec);
            }
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(mode, key);
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