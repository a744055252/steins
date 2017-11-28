package com.guanhuan.model;

import com.guanhuan.config.ResultStatus;
import lombok.Getter;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;

/**
 * 自定义返回结果
 * @date 2017-11-8 17:45:58
 */
@Getter
public class ResultModel<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private T content;


    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = null;
    }

    public ResultModel(int code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = null;
    }

    public ResultModel(ResultStatus status, T content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static <T> ResultModel ok(T content) {
        return new ResultModel<T>(ResultStatus.SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }

    /**
     * 将ResultModel转换为json格式的字符串
     * @Date: 16:29 2017/11/28
     * @param
     */
    public String toString(){
        return JSON.toJSONString(this);
    }
}
