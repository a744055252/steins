package com.guanhuan.spider.inter;

import java.util.List;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: 爬虫类的接口
 * @Date: 2017/10/15/015 22:24
 **/
public interface Spider {

    //获取当前商品以后的num个商品
    List<?> getMessages(int num);

    //获取已经爬取好的商品index开始位置，num数量
    List<?> getMessages(int index, int num);

    //获得所有以及爬取的商品
    List<?> getAllMessages();

    //得到平台
    String getTerrace();

    //得到爬取商品总数
    int getCurrentSize();

    boolean isEmpty();

    int size();

    //执行过的查询链接
    List<String> getQuery();

}
