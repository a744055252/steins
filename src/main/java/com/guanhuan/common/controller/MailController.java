package com.guanhuan.common.controller;

import com.guanhuan.component.mail.Mail;
import com.guanhuan.component.mail.MailUtil;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * 发送邮件使用
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-29 16:30
 **/
@RestController
@RequestMapping("/Mail")
public class MailController {

    @Autowired
    private MailUtil mailUtil;
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public ResultModel<?> sendMail(){
        Mail mail = Mail.builder()
                .from("liguanhuan_a@163.com")
                .fromName("冠焕")
                .toEmails(new String[]{"744055252@qq.com"})
                .toBcc(new String[]{"942741286@qq.com"})
                .subject("我的测试邮件")
                .data("测试邮件的内容，请马上收到并回复")
                .build();
        try {
            mailUtil.sendMail(mail);
        } catch (MessagingException e) {
            logger.error("发送email错误", e);
            return ResultModel.error(ResultStatus.USER_EMAIL_ERROR);
        }
        return ResultModel.ok();
    }
}
