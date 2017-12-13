package com.guanhuan.component.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 测试quartz的使用
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-13 16:04
 **/
public class MyJob extends QuartzJobBean{

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MyJob.class);
    
    private long timeout;

    public MyJob(long timeout) {
        this.timeout = timeout;
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("myJob running");
        System.out.println("myJob running");
    }
}
