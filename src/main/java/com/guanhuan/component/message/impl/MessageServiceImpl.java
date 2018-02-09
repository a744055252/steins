package com.guanhuan.component.message.impl;

import com.guanhuan.component.message.ConsumerService;
import com.guanhuan.component.message.ProducerService;
import com.guanhuan.component.message.destination.MyDestination;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 使用BlokingQueue实现,使用java.util.Observable实现观察者模式。
 * ConsumerService和ProducerService不应该直接在MessageServiceImpl里实现。
 *
 * @author liguanhuan_a@163.com
 * @see java.util.Observable
 * @see com.guanhuan.component.message.ConsumerService
 * @see com.guanhuan.component.message.ProducerService
 **/
public class MessageServiceImpl extends Observable implements ConsumerService, ProducerService{

    private Map<? super Destination,LinkedBlockingQueue<String>> destMap = null;

    private static final Destination DEFAULT_DESTINATION = new MyDestination();
    
    private ThreadPoolTaskExecutor executor;

    /** 数组初始容量 */
    private static final int CAPACITY = 20;

    public MessageServiceImpl(){
        this(CAPACITY, null, null);
    }

    public MessageServiceImpl(int capacity, Observer observer, ThreadPoolTaskExecutor executor) {
        super.addObserver(checkNotNull(observer));
        this.executor = checkNotNull(executor);
        this.destMap = new HashMap<Destination, LinkedBlockingQueue<String>>();
        this.destMap.put(DEFAULT_DESTINATION, new LinkedBlockingQueue<String>(capacity));
    }

    public void sendMessage(String message) throws Exception {
        sendMessage(message, DEFAULT_DESTINATION);
    }

    public void sendMessage(String message, Destination destination) throws Exception {
        LinkedBlockingQueue<String> destQueue = destMap.get(destination);
        if(destQueue == null){
            destQueue = new LinkedBlockingQueue<String>(CAPACITY);
            destMap.put(destination, destQueue);
        }
        destQueue.put(message);

        //触发前得修改状态
        super.setChanged();
        //触发所有的监听器
        final Observable observable = this;
        //开启线程，并发执行任务
        if (executor != null) {
            if(super.countObservers() != 0){
                //当没有传入线程池的时候，同步执行
                this.notifyObservers();
            }
            executor.execute(new Runnable() {
                public void run() {
                    observable.notifyObservers();
                }
            });
        }
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

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void setObserver(Observer observer){
        super.addObserver(observer);
    }
}
