package com.guanhuan.component.message;

import javax.jms.Destination;

/**
 * 消息消息生成服务接口
 *
 * @author guanhuan
 **/
public interface ConsumerService {

    /**
     * 获取默认队列数据,如果容器为空则阻塞，直至容器不为空
     * @return 返回队列中的数据
     * @throws Exception 获取数据异常
     */
    String getMessage() throws Exception;

    /**
     * 获取目标队列数据,如果容器为空则阻塞，直至容器不为空
     * @param destination 目标队列
     * @return 返回队列中的数据
     * @throws Exception 获取数据异常
     */
    String getMessage(Destination destination) throws Exception;
}
