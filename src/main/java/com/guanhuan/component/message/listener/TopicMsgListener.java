package com.guanhuan.component.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

/**
 * topic 监听器
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-29 14:47
 **/
public class TopicMsgListener implements SessionAwareMessageListener {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(TopicMsgListener.class);
    
    public void onMessage(Message message, Session session) throws JMSException {
        Destination destination = message.getJMSDestination();
        String topic = destination.toString();
        TextMessage textMessage = (TextMessage)message;
        logger.debug(topic+"_"+textMessage.getText());
    }
}
