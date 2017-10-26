package com.guanhuan.spider.service;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;


/**
 * httpclient服务
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-26 15:54
 **/
@Service
public class HttpClientService implements DisposableBean {

    private CloseableHttpClient client;

    private PoolingHttpClientConnectionManager manager;

    private HttpRequestRetryHandler handler;

    private int maxTotal = 200;

    private int maxPerRoute = 200;

    public  HttpClientService(){
        //连接池管理
        manager = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加
        manager.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        manager.setDefaultMaxPerRoute(maxPerRoute);
        // 将目标主机的最大连接数增加
//        manager.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        this.handler = handler;

        client = HttpClients.custom()
                .setConnectionManager(manager)
                .setRetryHandler(handler)
                .build();
    }

    public void destroy() throws Exception {
        if(manager != null)
            manager.shutdown();
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public void setClient(CloseableHttpClient client) {
        this.client = client;
    }

    public PoolingHttpClientConnectionManager getManager() {
        return manager;
    }

    public void setManager(PoolingHttpClientConnectionManager manager) {
        this.manager = manager;
    }

    public HttpRequestRetryHandler getHandler() {
        return handler;
    }

    public void setHandler(HttpRequestRetryHandler handler) {
        this.handler = handler;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        manager.setMaxTotal(maxTotal);
        this.maxTotal = maxTotal;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        manager.setDefaultMaxPerRoute(maxPerRoute);
        this.maxPerRoute = maxPerRoute;
    }
}
