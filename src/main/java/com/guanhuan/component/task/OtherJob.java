package com.guanhuan.component.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-12-13 17:33
 **/
public class OtherJob {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(OtherJob.class);

    public void doIt(){
        logger.info("doIt running");
        System.out.println("doIt running");
    }
}
