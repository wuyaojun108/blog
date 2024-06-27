package org.wyj.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt的工具类
 */
public class JwtUtil {
    private static final String JWT_TOKEN = "1qaz!QAZ12#$";
    private static final String USER_ID = "userId";
    // 1天的毫秒数 = 24小时 * 60分钟 * 60秒 *1000毫秒
    private static final int ONE_DAY_MILLIONS = 24 * 60 * 60 * 1000;

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ONE_DAY_MILLIONS));
        return jwtBuilder.compact();
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt jwt = Jwts.parser().setSigningKey(JWT_TOKEN).parse(token);
            return (Map<String, Object>)jwt.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
