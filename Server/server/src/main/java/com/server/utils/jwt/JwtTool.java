package com.server.utils.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtTool {
    // 固定使用HS256算法
    static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 密钥
    static final String pk = "zx client";
    // 签发者
    static final String issuer = "the client";
    // 默认签名有效时间 (单位：分钟)
    static final int validTime = 1;

    /*
        签发jwt，id为你要签发的用户的id
     */
    public static String createJwt(String id){
        long now = System.currentTimeMillis();
        long exp = now + 1000 * 60 * validTime;// 有效时间

        JwtBuilder builder = Jwts.builder()
                .setSubject(id)                         // jwt所面向的用户id
                .setIssuer(issuer)                      // 签发者
                .setIssuedAt(new Date(now))             // 签发时间
                .setExpiration(new Date(exp))           // 过期时间
                .signWith(signatureAlgorithm,pk);       // 签名算法及密钥
        return builder.compact();
    }

    public static JwtVerifyResult verifyJwt(String jwt){
        try{
            Claims cliams = Jwts.parser().setSigningKey(pk).parseClaimsJws(jwt).getBody();
            String id = cliams.getSubject();
            return new JwtVerifyResult(true,id);
        }
        catch(SignatureException e){
            return new JwtVerifyResult(false,"",JwtVerifyResult.InValidReason.Destroyed);
        }
        catch(ExpiredJwtException e){
            return new JwtVerifyResult(false,"", JwtVerifyResult.InValidReason.TimeOut);
        }
        catch (Exception e){
            return new JwtVerifyResult(false,"",JwtVerifyResult.InValidReason.Destroyed);
        }
    }
}
