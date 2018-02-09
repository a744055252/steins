package com.guanhuan.authorization.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * token
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-08 11:25
 **/
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Token {

    /** 用户id */
    private long userId;

    /** 用户的token */
    private String tokenStr;

    /** 创建时间 */
    private long createTime;

    /** 过期时间 */
    private long expirationTime;

}
