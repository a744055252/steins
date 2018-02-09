package com.guanhuan.component.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

/**
 * mail工具类
 *
 * @author liguanhuan_a@163.com
 * @since  2017-12-29 16:00
 **/
@Component
public class MailUtil {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfig freeMarkerConfigurer;

    public boolean sendMail(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage msg = getMimeMessage(mail);
        if (msg == null) {
            return false;
        }
        mailSender.send(msg);
        logger.info("send email :"+ mail.toString());
        return true;
    }

    private MimeMessage getMimeMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
        helper.setFrom(mail.getFrom(), mail.getFromName());

        helper.setSubject(mail.getSubject());
        if (mail.getToEmails() != null && mail.getToEmails().length != 0) {
            helper.setTo(mail.getToEmails());
        }
        if (mail.getToBcc() != null && mail.getToBcc().length != 0) {
            helper.setBcc(mail.getToBcc());
        }
        //这里将发送的邮件抄送一份给自己，可以避免网易554错误
        String[] cc = mail.getToCC();
        String[] cctemp;
        if (cc == null || cc.length == 0) {
            cctemp = new String[1];
            cctemp[0] = mail.getFrom();
        } else {
            cctemp = new String[cc.length+1];
            System.arraycopy(cc, 0, cctemp, 0, cc.length);
            cctemp[cc.length] = mail.getFrom();
        }
        helper.setCc(cctemp);
        //这里对模板进行解析
        helper.setText(FreeMarkerHtml(mail.getData(), mail.getTemplate()), true);
        return msg;
    }

    private String FreeMarkerHtml(Map<String, String> data,String tempName) throws IOException, TemplateException {
        String html;
        Template template = freeMarkerConfigurer.getConfiguration().
                getTemplate(tempName);
        html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        return html;
    }

}
