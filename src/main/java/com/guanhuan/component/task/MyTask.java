package com.guanhuan.component.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时执行的任务测试
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-13 11:05
 **/
//@Component
public class MyTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);

    /**
     * 每天11:20分执行任务
     * @Date: 11:11 2017/12/13
     */
    @Scheduled( fixedDelay = 1 * 1000L)
    public void task1(){
        logger.info("task1 running:" + System.currentTimeMillis()+ "_date:" + new Date(System.currentTimeMillis()).toString());
        System.out.println("task1 running");
    }

    @Scheduled( fixedRate= 5 * 1000L)
    public void task3() throws InterruptedException {
        logger.info("task3 fixedDelay running:" + System.currentTimeMillis()+ "_date:" + new Date(System.currentTimeMillis()).toString());
        TimeUnit.SECONDS.sleep(5L);
        System.out.println("task3 fixedDelay running");
    }

}
