package com.guanhuan.authorization.manager.impl;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.authorization.manager.TokenManager;
import com.guanhuan.entity.User;
import com.guanhuan.common.utils.JwtUtil;
import com.guanhuan.config.CheckStatus;
import com.guanhuan.config.Constants;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 通过Redis存储和验证token的实现类
 * @see com.guanhuan.authorization.manager.TokenManager
 * @author guanhuan_li
 * @date 2017-11-10 09:41:49.
 */
@Component
public class RedisTokenManager implements TokenManager {

    @Autowired
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);

    /**
     * 创建一个长时间有效的token，有效期为360小时（15天）
     * @Date: 17:46 2017/11/20
     * @param user
     */
    public String createToken(User user) {
        //创建token
        String subject = ""+user.getUserId();
        String token;
        token = JwtUtil.createJWT(subject);
        //存储到redis并设置过期时间
        redis.boundValueOps(""+user.getUserId()).set(token, Constants.TOKEN_EXPIRES_TIME, Constants.TOKEN_EXPIRES_UNIT);
        return token;
    }

    /**
     * 生成一个临时的token，有效期为3个小时
     * @Date: 17:48 2017/11/20
     * @param user
     */
    public String createTempToken(User user) {
        //创建token
        String subject = ""+user.getUserId();
        String token;
        try {
            token = JwtUtil.createJWT(subject);
        } catch (Exception e) {
            logger.error("Token创建异常:"+subject,e);
            throw new RuntimeException("Token创建异常:"+subject, e);
        }
        //存储到redis并设置过期时间
        redis.boundValueOps(""+user.getUserId()).set(token, Constants.TOKEN_EXPIRES_TIEM_TEMP, Constants.TOKEN_EXPIRES_UNIT);
        return token;
    }

    public CheckResult checkToken(String token) {
        CheckResult result = JwtUtil.parseJWT(token);
        if(result.getCode() < 0){
            //出现异常
            return result;
        } else {
            Claims claims = result.getClaims();
            if(claims == null){
                return CheckResult.error(CheckStatus.FAIL_OTHER);
            }
            if("".equals(claims.getSubject())){
                return CheckResult.error(CheckStatus.FAIL_SIGNATURE);
            }
            String token_redis = redis.boundValueOps(claims.getSubject()).get();
            //这里使用redis进行存储，如果没redis没有找到，即超时
            if (token_redis == null) {
                return CheckResult.error(CheckStatus.FAIL_EXPIRED);
            }
            //如果使用的token与redis存储的token_redis不同时
            if (!token_redis.equals(token)) {
                return CheckResult.error(CheckStatus.FAIL_SIGNATURE);
            }
            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            redis.boundValueOps(claims.getSubject()).expire(Constants.TOKEN_EXPIRES_TIME, Constants.TOKEN_EXPIRES_UNIT);
            return CheckResult.ok(claims);
        }
    }

    public void deleteToken(long userId) {
        redis.delete(""+userId);
    }
}
