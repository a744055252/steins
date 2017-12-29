package com.guanhuan.config;

/**
 * 自定义请求状态码
 * @author ScienJus
 * @date 2015/7/15.
 */
public enum ResultStatus {
    SUCCESS(200, "成功"),

    //系统错误
    SYS_ERROR(-1001, "系统错误"),
    SYS_ERROR_400(-400, "错误的请求"),
    SYS_ERROR_404(-404, "请求失败，没有找到相应的资源"),
    SYS_ERROR_405(-405, "不支持的请求方法"),
    SYS_ERROR_415(-415, "不支持的媒体类型"),
    SYS_ERROR_500(-500, "服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理"),


    //业务错误
    USERNAME_OR_PASSWORD_ERROR(-2001, "用户名或密码错误"),
    USER_NOT_FOUND(-2002, "用户不存在"),
    USER_NOT_LOGIN(-2003, "用户未登录"),
    USER_NOT_AUTH(-2004, "用户无权限"),
    USER_EXPIRES_AUTH(-2005, "用户权限已过期"),
    USER_TOKEN_ERROR(-2006, "用户token错误"),
    USER_OTHER_ERROR(-2007, "其他错误"),
    USER_EMAIL_ERROR(-2008, "发送email错误");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
