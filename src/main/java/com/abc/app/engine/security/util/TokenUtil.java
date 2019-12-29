package com.abc.app.engine.security.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * Token工具类
 */
@Slf4j
public class TokenUtil {

    private static final String CLAIM_USERNAME = "username";

    @Autowired
    private TokenConfig config;

    /**
     * 算法
     * 
     * @return
     */
    private Algorithm algorithm() {
        return Algorithm.HMAC256(config.getSecret());
    }

    /**
     * 创建Token
     * 
     * @param username
     * @return
     */
    public String create(String username) {
        return create(username, config.getExpiry());
    }

    /**
     * 创建Token
     * 
     * @param username
     * @param expiry
     * @return
     */
    public String create(String username, long expiry) {
        if (username == null) {
            return null;
        }
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiry * 1000);
        try {
            String token = JWT.create() //
                    .withIssuer(config.getIssuer()) // 签发人
                    .withIssuedAt(now) // 签发时间
                    .withNotBefore(now) // 开始时间
                    .withExpiresAt(exp) // 到期时间
                    .withClaim(CLAIM_USERNAME, username) // 用户名
                    .sign(algorithm());// 算法
            return token;
        } catch (Exception e) {
            log.error("创建TOKEN失败[{}]：{}", username, e.getMessage());
        }
        return null;
    }

    /**
     * 验证Token
     * 
     * @param token
     * @return
     */
    public boolean verify(String token) {
        if (token == null) {
            return false;
        }
        try {
            JWTVerifier verifier = JWT.require(algorithm()) // 算法
                    .withIssuer(config.getIssuer()) // 签发人
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("验证TOKEN失败[{}]：{}", token, e.getMessage());
        }
        return false;
    }

    /**
     * 解码Token
     * 
     * @param token
     * @return
     */
    public String getUsername(String token) {
        if (token == null) {
            return null;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            if (jwt != null) {
                return jwt.getClaim(CLAIM_USERNAME).asString();
            }
        } catch (Exception e) {
            log.error("解码TOKEN失败[{}]：{}", token, e.getMessage());
        }
        return null;
    }

    /**
     * 解码Token
     * 
     * @param token
     * @return
     */
    public Long getExpiry(String token) {
        if (token == null) {
            return null;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            if (jwt != null) {
                Date exp = jwt.getExpiresAt();
                Date nbf = jwt.getNotBefore();
                if (exp != null && nbf != null) {
                    long ms = exp.getTime() - nbf.getTime();
                    return ms / 1000;
                }
            }
        } catch (Exception e) {
            log.error("解码TOKEN失败[{}]：{}", token, e.getMessage());
        }
        return null;
    }

}