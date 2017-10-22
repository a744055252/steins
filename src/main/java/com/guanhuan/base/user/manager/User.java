package com.guanhuan.base.user.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户类
 *
 * @author guanhuan-li
 * @email liguanhuan_a@163.com
 * @date 2017年8月16日
 */
@Entity
@Getter
@Setter
@ToString
public class User {
    /**
     * 用户id
     */
    @Id
    private long userId;

    /**
     * 用户名字
     */
    @Size(min = 1, max = 15, message = "{user.userName.length.error}")
    private String userName;

    /**
     * 用户账号
     */
    @Pattern(regexp = "^[a-zA-Z][\\w]{5,14}$", message = "{user.account.format.error}")
    private String account;

    /**
     * 用户密码  长度为8到15位
     */
    @Size(min = 8, max = 15, message = "{user.userName.length.error}")
    private String password;

    /**
     * 用户创建时间
     */
    private String createTime;

    /**
     * 用户更新时间
     */
    private String updateTime;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户年龄
     */
    @Range(min = 0, max = 120, message = "{user.age.size.error}")
    private int age;

    /**
     * 用户邮箱
     */
    @Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$", message = "{user.email.format.error}")
    private String email;

    /**
     * 用户手机
     */
    @Pattern(regexp = "^[1][0-9]{10}$", message = "{user.phone.format.error}")
    private String phone;

}
