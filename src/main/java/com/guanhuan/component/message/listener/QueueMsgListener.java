package com.guanhuan.component.message.listener;

import com.guanhuan.component.mail.Mail;
import com.guanhuan.component.mail.MailUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * activemq 监听器
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-28 16:25
 **/
public class QueueMsgListener implements MessageListener {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(QueueMsgListener.class);

    @Autowired
    private MailUtil mailUtil;

    //当收到消息后，自动调用该方法,spring配置默认监听器，负责接收消息
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            Destination destination = tm.getJMSDestination();
            String text = tm.getText();
            System.out.println("---------消息消费---------");
            System.out.println("消息内容:\t" + text);
            System.out.println("消息ID:\t" + tm.getJMSMessageID());
            System.out.println("消息Destination:\t" + destination);
            System.out.println("---------更多信息---------");
            System.out.println(ToStringBuilder.reflectionToString(tm));
            System.out.println("-------------------------");
            logger.debug("监听到"+message.getJMSDestination().toString()+":"+tm.getText());
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
    }
}
