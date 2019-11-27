package com.xinda.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @author: aoliao
 * @updateTime: 2019/10/26 15:14
 */
public class JwtOperat {
    /**
     * @author: aoliao
     * @updateTime: 2019/10/26 15:16
     * 生成私匙
     */
    public String generalKey() throws UnsupportedEncodingException {
        String stringKey = "sfsahfsafsfjiuowiishfhyu78y";
        return Base64.getEncoder().encodeToString(stringKey.getBytes("UTF-8"));
    }
    /**
     * @author: aoliao
     * @param: id
     * @param: subject
     * @param: ttlMillis
     * @updateTime: 2019/10/26 15:20
     * @return: java.lang.String
     * 生成TOKEN
     */
    public String createToken(String id,//token唯一标识
                              String subject,//用户标识
                              long ttlMillis//有效期/分钟
    ) throws UnsupportedEncodingException {
        //加密方式
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // token创建时间
        long nowMillis = System.currentTimeMillis();
        //转换Date类型
        Date now = new Date(nowMillis);
        //私匙
        String key=generalKey();
        //设置时间
        ttlMillis*=60000;

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
    /**
     * @author: aoliao
     * @updateTime: 2019/10/26 15:15
     * @param :token
     * @return :Claims
     * 解码TOKEN
     */
    public Claims parseToken(String token) throws UnsupportedEncodingException {

        Claims claims= null;
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = generalKey();
        try {
            //得到DefaultJwtParser
            claims=Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(key)
                    //设置需要解析的token
                    .parseClaimsJws(token).getBody();
        }catch (Exception mes){
            return null;
        }
        return claims;
    }

    public boolean tokenExpired(String token) throws UnsupportedEncodingException {

        String key = generalKey();
        try {
            //如果过期会抛出异常
            Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(key)
                    //设置需要解析的token
                    .parseClaimsJws(token).getBody();
        }catch (Exception mes){
            return true;
        }
        return false;
    }
    public Map getSubject(String token) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Claims claims = parseToken(token);

        Map<String,String> subjectMap = mapper.readValue(claims.getSubject(),Map.class);

        return subjectMap;
    }

}
