package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import static com.google.common.base.Preconditions.checkNotNull;

//@Component
public class ProducerServiceActiveMQ implements ProducerService{

    @Autowired
    private JmsTemplate jmsTemplate;

    /** 默认队列 */
    @Autowired
    private Destination queueDestination;


    public void sendMessage(final String message) throws Exception {
        sendMessage(message, queueDestination);
    }

    public void sendMessage(final String message, Destination destination) throws Exception {
        checkNotNull(message, "destination不能为空");
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }


}