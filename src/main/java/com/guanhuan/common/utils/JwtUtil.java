package com.guanhuan.common.utils;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.config.CheckStatus;
import com.guanhuan.config.Constants;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jwt 工具类
 *
 * @author guanhuan-li
 * @since  2017年8月17日
 */
public class JwtUtil {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    /**
     * 这里用于加密的key是由MacProvider提供的，如果服务器重启，key就会改变，原先redis缓存的会无法成功校验
     * 调试使用不变的key generalKey()
     **/
//    private static final SecretKey key = MacProvider.generateKey();
    private static final SecretKey key = generalKey();


    /**
     * 由字符串生成加密key
     * @return SecretKey key
     */
    private static SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64(Constants.JWT_SECRET.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建jwt
     * @param subject 由id组成
     * @param time 时间
     * @param unit 单位
     * @return String token
     * @throws Exception exception
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
     * @return String token
     */
    public static String createJWT(String subject) {
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
     * @param jwt jwt
     * @return CheckResult
     */
    public static CheckResult parseJWT(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
        }catch (UnsupportedJwtException e) {
            //无法将jwt转换为claims
            return CheckResult.error(CheckStatus.FAIL_CLAIMS);
        } catch (MalformedJwtException e) {
            //该字符串不是一个有效的jwt字符串
            return CheckResult.error(CheckStatus.FAIL_MALFORMED);
        } catch (IllegalArgumentException e) {
            //该字符串为空
            return CheckResult.error(CheckStatus.FAIL_ISEMPTY);
        } catch (SignatureException e) {
            //签名错误
            return CheckResult.error(CheckStatus.FAIL_SIGNATURE);
        } catch (ExpiredJwtException e) {
            //jwt已经过期
            return CheckResult.error(CheckStatus.FAIL_EXPIRED);
        }

        return CheckResult.ok(claims);
    }


    public static void main(String[] args) throws Exception {
        CheckResult result = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MTExNjkxMjAsInN1YiI6IjYiLCJpc3MiOiJTdGVpbnMgR2F0ZSJ9.9GfkMazT-UjWZtCKYC-DfUCeL3yE9h4eRJwBMMP_-DQ");
        System.out.println(DateUtil.getYMDHMSDate(result.getClaims().getIssuedAt().getTime()));

        String token = JwtUtil.createJWT("6");
        System.out.println("1:"+token);
        System.out.println("2:eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MTExNjkxMjAsInN1YiI6IjYiLCJpc3MiOiJTdGVpbnMgR2F0ZSJ9.9GfkMazT-UjWZtCKYC-DfUCeL3yE9h4eRJwBMMP_-DQ");
        CheckResult tokenResult = JwtUtil.parseJWT(token);
        System.out.println(DateUtil.getYMDHMSDate(tokenResult.getClaims().getIssuedAt().getTime()));

    }
}
