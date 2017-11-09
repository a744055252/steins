package com.guanhuan.common.utils;

import com.guanhuan.config.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jwt 工具类
 * @Auther: guanhuan_li
 * @Date: 17:55 2017/11/9
 */
public class JwtUtil {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final SecretKey key = MacProvider.generateKey();

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    /**
     * 由字符串生成加密key
     * @return
     */
    private static SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64(Constants.JWT_SECRET.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param subject 由id组成
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    public static String createJWT(String subject, long time, TimeUnit unit) throws Exception {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setSubject(subject)
            .setIssuer(Constants.JWT_ISSUER)
            .signWith(signatureAlgorithm, key);
        if (time >= 0) {
            long expMillis = nowMillis + unit.toMillis(time);
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 创建jwt,不在token上设置超时时间，使用redis的有效时间进行代替
     * @param subject 由id组成
     * @return
     * @throws Exception
     */
    public static String createJWT(String subject) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(key.getEncoded());
        logger.info("create_key:"+sb.toString());
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(Constants.JWT_ISSUER)
                .signWith(signatureAlgorithm, key);
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(key.getEncoded());
        logger.info("create_key:"+sb.toString());
        Claims claims = Jwts.parser()
           .setSigningKey(key)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }

//    /**
//     * 验证JWT
//     * @param jwtStr
//     * @return
//     */
//    public static CheckResult validateJWT(String jwtStr) {
//        CheckResult checkResult = new CheckResult();
//        Claims claims = null;
//        try {
//            claims = parseJWT(jwtStr);
//            checkResult.setSuccess(true);
//            checkResult.setClaims(claims);
//        } catch (ExpiredJwtException e) {
//            checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
//            checkResult.setSuccess(false);
//        } catch (SignatureException e) {
//            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
//            checkResult.setSuccess(false);
//        } catch (Exception e) {
//            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
//            checkResult.setSuccess(false);
//        }
//        return checkResult;
//    }

    public static void main(String[] args){
        System.out.println(TimeUnit.SECONDS.toMillis(3));


    }
}
