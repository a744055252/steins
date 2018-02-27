package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.jms.Destination;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * kafka实现的Producer
 *
 * @author liguanhuan_a@163.com
 * @create 2018-01-04 10:32
 **/
public class ProducerServiceKafka implements ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) throws Exception {
        kafkaTemplate.sendDefault(message);
    }

    public void sendMessage(String message, Destination destination) throws Exception {
        checkNotNull(destination, "destination不能为空");
        kafkaTemplate.send(destination.getClass().getSimpleName(), message);
    }
}
