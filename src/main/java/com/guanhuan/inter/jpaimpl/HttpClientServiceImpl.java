package com.guanhuan.inter.jpaimpl;

import com.guanhuan.inter.HttpClientService;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * httpclient服务
 *
 * @author liguanhuan_a@163.com
 * @since 2017-10-26 15:54
 **/
@Service
public class HttpClientServiceImpl implements DisposableBean,HttpClientService {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    private CloseableHttpClient client;

    private PoolingHttpClientConnectionManager manager;

    private HttpRequestRetryHandler handler;

    private int maxTotal = 200;

    private int maxPerRoute = 200;

    public HttpClientServiceImpl(){

    }

    public HttpClientServiceImpl(int maxTotal, int maxPerRoute, HttpRequestRetryHandler handler){
        //连接池管理
        manager = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加
        manager.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        manager.setDefaultMaxPerRoute(maxPerRoute);
        // 将目标主机的最大连接数增加
//        entity.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        this.maxPerRoute = maxPerRoute;
        this.maxTotal = maxTotal;
        this.handler = handler;

        client = HttpClients.custom()
                .setConnectionManager(manager)
                .setRetryHandler(handler)
                .build();

        logger.debug("client:" + client.toString() + "\r\n"
                + "manager:" + manager.toString() + "\r\n"
                + "handler:" + handler.toString());
    }

    public void release() {
        manager.closeExpiredConnections();
    }

    public void destroy() throws Exception {
        if(manager != null)
            manager.shutdown();
    }

    public CloseableHttpResponse execute(HttpRequestBase request) throws IOException {
        return client.execute(request);
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public PoolingHttpClientConnectionManager getManager() {
        return manager;
    }

    public HttpRequestRetryHandler getHandler() {
        return handler;
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

}
