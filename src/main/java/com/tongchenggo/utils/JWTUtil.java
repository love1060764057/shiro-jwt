package com.tongchenggo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tongchenggo.constant.Constant;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/*
* token生成、验证工具类
* @author jiang
* @date 2021.6.11
* */
public class JWTUtil {
    //有效期
    private static final long EXPIRE_TIME = 60*1000;
    //密钥
    private static final String SECRET = Constant.SECRET;

    /*
     * 校验token是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            //效验token
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


        /*
         * 获得token中的用户名
         */
        public static String getUsername(String token) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim("username").asString();
            } catch (JWTDecodeException e) {
                return null;
            }
        }

        /*
         * 生成签名
         */
         public static String sign(String username) throws UnsupportedEncodingException {
             Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
             Algorithm algorithm = Algorithm.HMAC256(SECRET);
             Date date1 = new Date();
             return JWT.create()
                     .withIssuer("auth0")//签发人
                     .withClaim("username", username)//载体数据
                     .withIssuedAt(date1)//签发时间
                     .withExpiresAt(date)//过期时间
                     .sign(algorithm);//生成新的JWT
         }
}
