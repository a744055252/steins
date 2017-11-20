package com.guanhuan.authorization.entity;

import com.guanhuan.config.CheckStatus;
import io.jsonwebtoken.Claims;
import lombok.*;

/**
 * jwt的检查结果
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-08 17:44
 **/
@Getter
@RequiredArgsConstructor()
@AllArgsConstructor
@ToString
public class CheckResult {

    /** 检查结果码 */
    private int code;

    /** 检查结果信息 */
    private String message;

    /** 解析后的claims */
    private Claims claims;

    public CheckResult(CheckStatus checkStatus){
        this.code = checkStatus.getCode();
        this.message = checkStatus.getMessage();
    }

    public CheckResult(CheckStatus checkStatus, Claims claims){
        this.code = checkStatus.getCode();
        this.message = checkStatus.getMessage();
        this.claims = claims;
    }

    public static CheckResult ok(Claims claims){
        return new CheckResult(CheckStatus.SUCCESS, claims);
    }

    public static CheckResult ok(){
        return new CheckResult(CheckStatus.SUCCESS);
    }

    public static CheckResult error(CheckStatus error){
        return new CheckResult(error);
    }

}
