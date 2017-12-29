package com.guanhuan.component.message.listener;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * activemq 监听器
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-28 16:25
 **/
public class QueueMsgListener implements MessageListener {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(QueueMsgListener.class);

    //当收到消息后，自动调用该方法,spring配置默认监听器，负责接收消息
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("---------消息消费---------");
            System.out.println("消息内容:\t" + tm.getText());
            System.out.println("消息ID:\t" + tm.getJMSMessageID());
            System.out.println("消息Destination:\t" + tm.getJMSDestination());
            System.out.println("---------更多信息---------");
            System.out.println(ToStringBuilder.reflectionToString(tm));
            System.out.println("-------------------------");
            logger.debug("监听到"+message.getJMSDestination().toString()+":"+tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
