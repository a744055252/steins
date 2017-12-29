package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.ConsumerService;
import com.guanhuan.component.message.ProducerService;
import com.guanhuan.component.message.destination.MyDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用BlokingQueue实现
 *
 * @author liguanhuan_a@163.com
 **/
@Component("messageService")
public class MessageServiceImpl implements ConsumerService, ProducerService {

    private static Map<? super Destination,LinkedBlockingQueue<String>> destMap = null;

    private static final Destination DEFAULT_DESTINATION = new MyDestination();
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /** 数组初始容量 */
    private static final int CAPACITY = 20;

    public MessageServiceImpl(){
        this(CAPACITY);
    }

    public MessageServiceImpl(int capacity) {
        destMap = new HashMap<Destination, LinkedBlockingQueue<String>>();
        destMap.put(DEFAULT_DESTINATION, new LinkedBlockingQueue<String>(capacity));
    }

    @SuppressWarnings("unchecked")
    public void sendMessage(String message) throws Exception {
        logger.debug("sendMessage:" + message);
        LinkedBlockingQueue<String> defaultQueue = (LinkedBlockingQueue<String>) destMap.get(DEFAULT_DESTINATION);
        defaultQueue.put(message);
    }

    public void sendMessage(String message, Destination destination) throws Exception {
        LinkedBlockingQueue<String> destQueue = destMap.get(destination);
        if(destQueue == null){
            destQueue = new LinkedBlockingQueue<String>(CAPACITY);
            destMap.put(destination, destQueue);
        }
        destQueue.put(message);
    }

    public String getMessage() throws Exception {
        LinkedBlockingQueue<String> defaultQueue = destMap.get(DEFAULT_DESTINATION);
        return defaultQueue.take();
    }

    public String getMessage(Destination destination) throws Exception {
        LinkedBlockingQueue<String> destQueue = destMap.get(destination);
        if(destQueue == null){
            destQueue = new LinkedBlockingQueue<String>(CAPACITY);
            destMap.put(destination, destQueue);
        }
        return destQueue.take();
    }

}
