package com.guanhuan.component.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 邮件实体
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-29 15:50
 **/
@Setter
@Getter
@Builder
@ToString
public class Mail {
    /** 发件人 */
    private String from;
    /** 发件人名 */
    private String fromName;
    /** 收件人 */
    private String[] toEmails;
    /** 抄送 */
    private String[] toCC;
    /** 暗送 */
    private String[] toBcc;
    /** 主题 */
    private String subject;
    /** 邮件数据 */
//    private Map data ;
    private String data;
    /** 邮件模板 */
    private String template;

}
