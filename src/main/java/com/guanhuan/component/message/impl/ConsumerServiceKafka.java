package com.guanhuan.component.message.impl;


import com.guanhuan.component.message.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.jms.Destination;

/**
 * kafka实现的Cosumer
 *
 * @author liguanhuan_a@163.com
 * @create 2018-01-04 10:20
 **/
public class ConsumerServiceKafka implements ConsumerService{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String getMessage() throws Exception {
        return null;
    }

    public String getMessage(Destination destination) throws Exception {
        return null;
    }
}
