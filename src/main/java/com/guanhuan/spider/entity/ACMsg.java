package com.guanhuan.spider.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: 爬取的消息
 * @Date: 2017/10/15/015 20:35
 **/
@Entity
@Getter
@Setter
@ToString
@Table
public class ACMsg {

    /** 消息的id */
    @Id
    @GeneratedValue
    private long id;

    /** 消息在平台的id */
    private String terraceId;

    /** 封面图片链接 */
    private String imageUrl;

    /** 消息创建作者 */
    private String auther;

    /** 消息创建时间 */
    private String createTime;

    /** 消息爬取时间 */
    private String spiderTime;

    /** 消息标题 */
    private String title;

    /** 消息点击量 */
    private long click;

    /** 消息评论量 */
    private long review;

    /** 消息的分类 用，分割*/
    private String type;

    /** 消息详细的链接 */
    private String acUrl;

}
