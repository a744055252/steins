package com.guanhuan.component.task;

import com.guanhuan.entity.AcfunSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 定时爬取任务
 *
 * @author liguanhuan_a@163.com
 * @create 2017-12-18 16:21
 **/
@Component("spiderTask")
public class SpiderTask {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(SpiderTask.class);

    @Autowired
    AcfunSpider acfunSpider;

    public void spiderAcfun() throws IOException {
        acfunSpider.running();
        logger.info("acfun spider success!");
    }

}
