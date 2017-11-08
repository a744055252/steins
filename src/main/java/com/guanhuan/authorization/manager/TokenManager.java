package com.guanhuan.authorization.manager;

import com.guanhuan.authorization.entity.Token;
import com.guanhuan.base.user.entity.User;
import io.jsonwebtoken.Claims;

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
    public String createToken(User user);

    /**
     * 检查token是否有效
     * @param token token
     * @return 是否有效
     */
    public boolean checkToken(String token);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public Claims getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(long userId);

}
