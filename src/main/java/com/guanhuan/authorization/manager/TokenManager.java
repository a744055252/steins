package com.guanhuan.authorization.manager;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.entity.User;

/**
 * 对Token进行操作的接口
 * @date 2017-11-8 14:45:59
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     * @param user 指定用户
     * @return 生成的token
     */
    String createToken(User user);

    /**
     * 检查token是否有效,并返回结果
     * @param token 加密后的字符串
     * @return CheckResult
     */
    CheckResult checkToken(String token);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    void deleteToken(long userId);

}
