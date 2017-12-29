package com.guanhuan.component.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mail工具类
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-29 16:00
 **/
@Component
public class MailUtil {

    private static String DEFAULT_ENCODING = "UTF-8";

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(Mail mail) throws MessagingException {
        MimeMessage msg = getMimeMessage(mail);
        if (msg == null) {
            return false;
        }
        mailSender.send(msg);
        logger.info("send email :"+ mail.toString());
        return true;
    }

    private MimeMessage getMimeMessage(Mail mail) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
        try {
            helper.setFrom(mail.getFrom(), mail.getFromName());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        helper.setSubject(mail.getSubject());
        helper.setTo(mail.getToEmails());
        helper.setBcc(mail.getToBcc());
        //这里将发送的邮件抄送一份给自己，可以避免网易554错误
        List<String> cc;
        if(mail.getToCC() != null) {
            cc = Arrays.asList(mail.getToCC());
        } else {
            cc = new ArrayList<String>();
        }
        cc.add(mail.getFrom());
        String[] cctemp = new String[cc.size()+1];
        helper.setCc(cc.toArray(cctemp));
        //这里对模板进行解析
        helper.setText(mail.getData(), true);
        return msg;
    }
}
