package com.guanhuan.spider.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 爬虫登录的用户
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-27 17:15
 **/
@Entity
@Getter
@Setter
@ToString
@Table
public class SpiderUser implements Serializable{

    /** id */
    @Id
    @GeneratedValue
    private long id;

    /** 用户所属的平台 */
    private String terrace;

    /** 用户账号 */
    private String account;

    /** 用户密码 */
    private String password;

    /** 是否生效 */
    private int status;
}
