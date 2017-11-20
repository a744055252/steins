package com.guanhuan.config;

/**
 * jwt的检查结果码
 *
 * @author
 * @create 2017-11-08 17:18
 **/
public enum CheckStatus {

    /** 校验成功 */
    SUCCESS(1,"成功"),

    /** 校验失败，超时 */
    FAIL_EXPIRED(-1, "超时"),

    /** 校验失败，签名错误 */
    FAIL_SIGNATURE(-2, "签名错误"),

    /** 校验失败，无法转换为claims */
    FAIL_CLAIMS(-3, "无法转换为claims"),

    /** 校验失败，该字符串不是一个有效的jwt字符串 */
    FAIL_MALFORMED(-4, "该字符串不是一个有效的jwt字符串"),

    /** 校验失败，该字符串为空 */
    FAIL_ISEMPTY(-5,"该字符串为空"),

    /** 校验失败，其他错误 */
    FAIL_OTHER(-6, "其他错误");

    /** 返回码 */
    private int code;

    /** 返回结果描述 */
    private String message;


    CheckStatus(int code, String message){
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
