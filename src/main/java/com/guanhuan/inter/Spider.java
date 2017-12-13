package com.guanhuan.inter;

import java.io.IOException;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: 爬虫类的接口
 * @Date: 2017/10/15/015 22:24
 **/
public interface Spider {

    String TERRACE_ACFUN = "acfun";

    int STATE_EFFECTIVE = 1;

    int STATE_INVALID = 0;

    /**
     * 当爬取的信息需要登录时，进行登录
     * @Date: 16:50 2017/10/27
     */
    boolean login() throws IOException;

    /**
     * 爬取数据并存入数据库
     * @Date: 16:51 2017/10/27
     */
    boolean running() throws IOException;

    /**
     * 得到平台
     * @Date: 17:00 2017/10/27
     */
    String getTerrace();

    /**
     * 得到平台
     * @Date: 17:00 2017/10/27
     */
    boolean isEmpty();

    /**
     * 得到爬取商品的总数
     * @Date: 17:00 2017/10/27
     */
    int size();


}
