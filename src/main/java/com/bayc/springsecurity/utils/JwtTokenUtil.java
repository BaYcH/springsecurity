package com.bayc.springsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author bayc
 * @className JwtTokenUtil
 * @Description
 * @date 2021/2/25 上午9:55
 */
public class JwtTokenUtil {
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    private static String tokenSignKey = "123456";
    private static String userRoleKey = "userRole";

    public String createToken(String userName) {
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createToken(String userName, String role) {
        String token = Jwts.builder().setSubject(userName)
                .claim(userRoleKey, role)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();

        return token;
    }

    public static String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public static String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        return claims.get(userRoleKey).toString();
    }

}