package com.guanhuan.component.message;

import javax.jms.Destination;

/**
 * 消息消息生成服务接口
 *
 * @author guanhuan
 *
 **/
public interface ProducerService {

    /**
     * 向默认队列发送数据
     * @param message 需要加入队列的数据
     * @throws Exception 发送数据出错
     */
    void sendMessage(final String message) throws Exception;

    /**
     * 向默认队列发送数据
     * @param message 需要加入队列的数据
     * @param destination 加入的目标队列
     * @throws Exception 发送数据出错
     */
    void sendMessage(final String message, Destination destination) throws Exception;

}
