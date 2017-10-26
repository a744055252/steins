package com.guanhuan.spider.bean;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

/**
 * 用于创建httpclient服务
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-26 16:38
 **/
@Component
public class MyHttpClient {
    private CloseableHttpClient client;

    private PoolingHttpClientConnectionManager manager;

    private HttpRequestRetryHandler handler;


}
