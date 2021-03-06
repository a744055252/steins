package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.TextMessage;

import static com.google.common.base.Preconditions.checkNotNull;


//@Component
public class ConsumerServiceActiveMQ implements ConsumerService{

    @Autowired
    private JmsTemplate jmsTemplate;

    /** 默认队列 */
    @Autowired
    private Destination queueDestination;

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceActiveMQ.class);

    public String getMessage() throws Exception {
        return getMessage(queueDestination);
    }

    public String getMessage(Destination destination) throws Exception {
        checkNotNull(destination, "destination不能为空");
        TextMessage message = (TextMessage) jmsTemplate.receive(destination);
        checkNotNull(message,"无法获取消息队列数据(超时或异常)");
        String result = message.getText();
        message.acknowledge();
        return result;
    }

}