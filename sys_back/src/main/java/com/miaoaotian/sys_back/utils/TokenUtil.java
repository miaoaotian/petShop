package com.miaoaotian.sys_back.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TokenUtil {
    public static String generateToken(int userId) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + Constant.EXPIRE_TIME); // 设置过期时间
        Algorithm algorithm = Algorithm.HMAC256(Constant.SECRET_KEY);

        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public static int parseToken(String token) {
        try {
            String jwt = token.substring(7);
            Algorithm algorithm = Algorithm.HMAC256(Constant.SECRET_KEY);
            return Integer.valueOf(JWT.require(algorithm)
                    .build()
                    .verify(jwt)
                    .getSubject());
        } catch (Exception e) {
            throw new RuntimeException("token失效");
        }
    }

    // 获取Token的过期时间
    public static long getExpirationTime(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constant.SECRET_KEY);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return decodedJWT.getExpiresAt().getTime();
        } catch (Exception e) {
            log.error("token解析出问题了", e);
            throw new RuntimeException("Token解析失败");
        }
    }
}