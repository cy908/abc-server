package com.abc.app.engine.common.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;

/**
 * RSA加解密工具：公钥加密、私钥解密、私钥签名、公钥验签
 * 
 * @author 陈勇
 * @date 2019年10月29日
 * 
 */
@Slf4j
public class RSAUtil {

    public static final int[] KEY_SIZES = { 1024, 2048 };
    public static final int KEY_SIZE = 1024;
    public static final String PUBLIC_KEY = "PublicKey";
    public static final String PRIVATE_KEY = "PrivateKey";

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String SIGNATURE = "SHA1withRSA";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 初始化密钥 {@link #KEY_SIZE}
     * 
     * @return
     */
    public static Map<String, Key> initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 初始化密钥
     * 
     * @param keySize
     * @return
     */
    public static Map<String, Key> initKey(int keySize) {
        if (Arrays.stream(KEY_SIZES).noneMatch(item -> item == keySize)) {
            log.error("密钥长度错误！");
            return null;
        }
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGen.initialize(keySize);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Key> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            log.info("{}", publicKey.getModulus().bitLength());
            log.info("{}", privateKey.getModulus().bitLength());
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
     * 获取密钥大小
     * 
     * @param key
     * @return
     */
    public static int getKeySize(RSAKey key) {
        int keySize;
        int bitLength = key.getModulus().bitLength();
        if (bitLength <= 512) {
            keySize = 0;
        } else if (bitLength <= 1024) {
            keySize = 1024;
        } else if (bitLength <= 2048) {
            keySize = 2048;
        } else if (bitLength <= 4096) {
            keySize = 4096;
        } else {
            keySize = 0;
        }
        return keySize;
    }

    /**
     * 公钥加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            int keySize = getKeySize(publicKey);
            int maxEncryptBlock = keySize / 8 - 11;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] inputData = data.getBytes(UTF_8);
            int inputLen = inputData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offset > 0) {
                if (inputLen - offset > maxEncryptBlock) {
                    cache = cipher.doFinal(inputData, offset, maxEncryptBlock);
                } else {
                    cache = cipher.doFinal(inputData, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * maxEncryptBlock;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return new String(Base64.getEncoder().encode(encryptedData), UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私钥解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            int keySize = getKeySize(privateKey);
            int maxDecryptBlock = keySize / 8;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] inputData = Base64.getDecoder().decode(data.getBytes(UTF_8));
            int inputLen = inputData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offset > 0) {
                if (inputLen - offset > maxDecryptBlock) {
                    cache = cipher.doFinal(inputData, offset, maxDecryptBlock);
                } else {
                    cache = cipher.doFinal(inputData, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * maxDecryptBlock;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私钥签名
     * 
     * @param data
     * @param key
     * @return
     */
    public static String sign(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE);
            signature.initSign(privateKey);
            signature.update(data.getBytes(UTF_8));
            return new String(Base64.getEncoder().encode(signature.sign()), UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 公钥验签
     * 
     * @param data
     * @param key
     * @param sign
     * @return
     */
    public static boolean verify(String data, String key, String sign) {
        if (data == null || key == null || sign == null) {
            return false;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign.getBytes(UTF_8)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

}