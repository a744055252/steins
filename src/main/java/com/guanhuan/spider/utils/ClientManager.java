package com.guanhuan.spider.utils;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 模拟浏览器
 * @author liguanhuan_a@163.com
 * @create 2017-10-24 16:02
 **/
public class ClientManager {

    /** 保持运行 */
    private static final long KEEP_RUNNING = -1;

    /** httpclient对象 */
    private static volatile CloseableHttpClient client = null;

    /** 浏览器运行时长,单位:milliseconds*/
    private static long runningTime = 0;

    private static HttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    private ClientManager(){}

    /**
     * 获取一个可以定时关闭的浏览器对象
     * @Date: 17:37 2017/10/24
     * @param time 浏览器运行时长
     */
    public static CloseableHttpClient getClient(long time){

        runningTime = time;

        if(client == null){
            synchronized (ClientManager.class){
                if(client == null){
                    client = HttpClients.createDefault();
                }
            }
        }

        //设置定时关闭任务
        if( time != KEEP_RUNNING){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(client != null) {
                        try {
                            close();
                            System.out.println("client close！");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, time);
        }

        return client;
    }

    /**
     * 获取一个可以定时关闭的浏览器对象
     * @Date: 17:39 2017/10/24
     * @param time 运行时长
     * @param unit 运行时长的单位
     */
    public static CloseableHttpClient getClient(long time, TimeUnit unit){
        return getClient(unit.toMillis(time));
    }

    /**
     * 关闭浏览器
     * @Date: 17:43 2017/10/24
     * @param
     */
    public static void close() throws IOException {
        if(client != null){
            client.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CloseableHttpClient client = ClientManager.getClient(5, TimeUnit.SECONDS);
        System.out.println("before:"+client);
        Thread.sleep(8*1000);
        System.out.println("after:"+client);
    }
}
