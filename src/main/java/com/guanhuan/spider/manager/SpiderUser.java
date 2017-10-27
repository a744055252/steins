package com.guanhuan.spider.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="spiderUser")
public class SpiderUser {

    /** id */
    @Id
    private long id;

    /** 用户所属的平台 */
    private String terrace;

    /** 用户账号 */
    private String account;

    /** 用户密码 */
    private String password;

    /** 是否生效 */
    private int state;
}
