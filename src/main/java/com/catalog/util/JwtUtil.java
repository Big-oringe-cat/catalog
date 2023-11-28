package com.catalog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

//import com.auth0.jwt.JWTVerifier;
//import com.vw.common.base.Constant;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

public class JwtUtil {

    /**
     * 过期时间改为从配置文件获取
     */
    private static String accessTokenExpireTime = "28800";

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey = "S29uZ2hlaGFuMjAxOVFXRUFTRFpEUw==";

//    @Value("${jwt.accessTokenExpireTime}")
//    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
//        com.vw.utils.JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
//    }
//
//    @Value("${jwt.encryptJWTKey}")
//    public void setEncryptJWTKey(String encryptJWTKey) {
//        com.vw.utils.JwtUtil.encryptJWTKey = encryptJWTKey;
//    }

    /**
     * 校验token是否正确
     *
     * @param token Token
     * @return boolean 是否正确
     */
//    public static boolean verify(String token) throws UnsupportedEncodingException {
//        String secret = getClaim(token, Constant.ACCOUNT) + Base64ConvertUtil.decode(encryptJWTKey);
//
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//        verifier.verify(token);
//        return true;
//    }


    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return java.lang.String
     */
    public static String getClaim(String token, String claim) {

        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(claim).asString();
    }

    /**
     * 解析jwt时候，如果解析失败，返回默认值
     *
     * @param token
     * @param claim
     * @return
     */
    public static String getClaimDefault(String token, String claim) {
        try {
            return getClaim(token, claim);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 生成签名
     *
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     */

    public static String sign(String account, long fromTimeMillis, int expiredSeconds) {
        try {
            String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
            Date date = new Date(fromTimeMillis + expiredSeconds * 1000L);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("currentTimeMillis", String.valueOf(fromTimeMillis))
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new JWTCreationException("decode jwt security key failed", e);
        }

    }

    public static String sign(String account, int expiredInSecond) {
        return sign(account, System.currentTimeMillis(), expiredInSecond);
    }

    public static String sign(String account) {
        return sign(account, System.currentTimeMillis(), Integer.parseInt(accessTokenExpireTime));
    }

    public static String sign(String account, String currentTimeMillis) throws UnsupportedEncodingException {
        String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
        Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000L);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withClaim("account", account)
                .withClaim("currentTimeMillis", String.valueOf(currentTimeMillis))
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }

}