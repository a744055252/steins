package com.guanhuan.config;

import com.google.common.base.Preconditions;

/**
 * jwt的检查结果码
 *
 * @author guanhuan_li
 * @since  2017-11-08 17:18
 **/
public enum CheckStatus {

    /** 校验成功 */
    SUCCESS(1,"成功"),

    /** 校验失败，token过期 */
    FAIL_EXPIRED(-1, "token过期"),

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


    // 根据code返回枚举类型,主要在switch中使用
    public static CheckStatus getByValue(int code) {
        CheckStatus checkStatus = null;
        for (CheckStatus temp : values()) {
            if (temp.getCode() == code) {
                checkStatus = temp;
            }
        }
        return Preconditions.checkNotNull(checkStatus);
    }
}
