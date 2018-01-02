package com.guanhuan.controller;

import com.guanhuan.component.mail.Mail;
import com.guanhuan.component.mail.MailUtil;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件使用,需要完善
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "冠焕");
        map.put("age", "23");
        Mail mail = Mail.builder()
                .from("liguanhuan_a@163.com")
                .fromName("冠焕")
                .toEmails(new String[]{"942741286@qq.com"})
                .subject("冠焕你好。")
                .data(map)
                .template("success.ftl")
                .build();
        try {
            mailUtil.sendMail(mail);
        } catch (MessagingException e) {
            logger.error("发送email错误", e);
            return ResultModel.error(ResultStatus.USER_EMAIL_ERROR);
        } catch (TemplateException e1 ) {
            logger.error("email FreeMarker模板错误!", e1);
            return ResultModel.error(ResultStatus.USER_EMAIL_ERROR);
        } catch (IOException e2) {
            logger.error("email FreeMarker模板错误!", e2);
            return ResultModel.error(ResultStatus.USER_EMAIL_ERROR);
        }
        return ResultModel.ok();
    }
}
