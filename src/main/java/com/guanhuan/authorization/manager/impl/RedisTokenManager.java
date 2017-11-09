package com.guanhuan.authorization.manager.impl;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.authorization.manager.TokenManager;
import com.guanhuan.base.user.entity.User;
import com.guanhuan.common.utils.JwtUtil;
import com.guanhuan.config.CheckStatus;
import com.guanhuan.config.Constants;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 * @see com.guanhuan.authorization.manager.TokenManager
 * @author ScienJus
 * @date 2015/7/31.
 */
@Component
public class RedisTokenManager implements TokenManager {

    @Autowired
    private RedisTemplate<String, String> redis;

    private static final Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);

    public String createToken(User user) {
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
        redis.boundValueOps(""+user.getUserId()).set(token, Constants.TOKEN_EXPIRES_TIME, Constants.TOKEN_EXPIRES_UNIT);
        return token;
    }

    public CheckResult checkToken(String token) {
        Claims claims;
        if (token == null) {
            return CheckResult.error(CheckStatus.FAIL_OTHER);
        }
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            return CheckResult.error(CheckStatus.FAIL_OTHER);
        }
        if(claims == null || "".equals(claims.getSubject())){
            return CheckResult.error(CheckStatus.FAIL_CLAIMS);
        }
        String token_redis = redis.boundValueOps(claims.getSubject()).get();
        //如果使用的token与redis存储的token_redis不同时
        //这里使用redis进行存储，如果没redis没有找到，即超时
        if (token_redis == null) {
            return CheckResult.error(CheckStatus.FAIL_EXPIRED);
        }
        if (!token_redis.equals(token)) {
            return CheckResult.error(CheckStatus.FAIL_SIGNATURE);
        }

//        如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(claims.getSubject()).expire(Constants.TOKEN_EXPIRES_TIME, Constants.TOKEN_EXPIRES_UNIT);
        return CheckResult.ok(claims);
    }

    public void deleteToken(long userId) {
        redis.delete(""+userId);
    }
}
