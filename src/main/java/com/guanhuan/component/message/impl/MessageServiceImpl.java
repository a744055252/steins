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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用BlokingQueue实现,使用java.util.Observable实现观察者模式。
 *
 * @author liguanhuan_a@163.com
 * @see java.util.Observable
 * @see com.guanhuan.component.message.ConsumerService
 * @see com.guanhuan.component.message.ProducerService
 **/
@Component("messageService")
public class MessageServiceImpl extends Observable implements ConsumerService, ProducerService{

    private static Map<? super Destination,LinkedBlockingQueue<String>> destMap = null;

    private static final Destination DEFAULT_DESTINATION = new MyDestination();
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /** 数组初始容量 */
    private static final int CAPACITY = 20;

    public MessageServiceImpl(){
        this(CAPACITY, null);
    }

    public MessageServiceImpl(int capacity, Observer observer) {
        super.addObserver(observer);
        destMap = new HashMap<Destination, LinkedBlockingQueue<String>>();
        destMap.put(DEFAULT_DESTINATION, new LinkedBlockingQueue<String>(capacity));
    }

    @SuppressWarnings("unchecked")
    public void sendMessage(String message) throws Exception {
        logger.debug("sendMessage:" + message);
        LinkedBlockingQueue<String> defaultQueue = destMap.get(DEFAULT_DESTINATION);
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
