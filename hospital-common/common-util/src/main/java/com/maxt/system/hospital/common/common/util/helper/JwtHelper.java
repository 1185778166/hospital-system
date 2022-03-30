package com.maxt.system.hospital.common.common.util.helper;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午2:42
 * @Version 1.0
 * @Description
 */
public class JwtHelper {
    //过期时间
    private static long tokenExpiration = 24*60*60*1000;
    //签名密钥
    private static String tokenSignKey = "123456";

    //根据参数生成token
    public static String createToken(Long userId, String userName){
        String token = Jwts.builder()
                .setSubject("HOSPITAL-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //根据token字符串得到用户id
    public static Long getUserId(String token){
        if (!StringUtils.hasLength(token)){
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    //根据token字符串得到用户名称
    public static String getUserName(String token){
        if (!StringUtils.hasLength(token)){
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String userName = (String) claims.get("userName");
        return userName;
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "maxt");
        System.out.println(token);
        System.out.println(getUserId(token));
        System.out.println(getUserName(token));
    }
}
