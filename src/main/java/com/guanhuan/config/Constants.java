package com.guanhuan.config;

import java.util.concurrent.TimeUnit;

/**
 * 常量
 * @author ScienJus
 * @since 2015/7/31.
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token的有效期
     */
    public static final long TOKEN_EXPIRES_TIME = 360;

    /**
     * 临时token的有效期
     */
    public static final long TOKEN_EXPIRES_TIEM_TEMP = 3;

    /**
     * token有效期单位
     */
    public static final TimeUnit TOKEN_EXPIRES_UNIT = TimeUnit.HOURS;
    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";


    /**
     * jwt
     *
     */
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "It's all the choice of the Steins Gate";
    public static final String JWT_ISSUER = "Steins Gate";


    public static final int JWT_TTL = 60*60*1000;  //millisecond
    public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 12*60*60*1000;  //millisecond

}
