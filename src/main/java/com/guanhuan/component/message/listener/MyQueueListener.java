package com.guanhuan.component.message.listener;

import com.guanhuan.component.mail.Mail;
import com.guanhuan.component.mail.MailUtil;
import com.guanhuan.component.message.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * MessageServiceImpl的监听器，用于监听是否存有数据
 *
 * @author liguanhuan_a@163.com
 * @create 2018-01-02 17:44
 **/
//@Component
public class MyQueueListener implements Observer {

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private Destination emailDestination;

    @Autowired
    private MailUtil mailUtil;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MyQueueListener.class);
    
    public void update(Observable o, Object arg) {
        String text;
        try {
            text =  messageService.getMessage(emailDestination);
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "光平");
            map.put("age", "23");
            Mail mail = Mail.builder()
                    .from("liguanhuan_a@163.com")
                    .fromName("冠焕")
                    .toEmails(new String[]{text})
                    .subject("光平你好。")
                    .data(map)
                    .template("success.ftl")
                    .build();
            mailUtil.sendMail(mail);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        logger.debug("Listener running!");
    }
}
