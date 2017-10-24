package com.guanhuan.spider.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 模拟浏览器
 * @author liguanhuan_a@163.com
 * @create 2017-10-24 16:02
 **/
public class ClientManager {

    /** 保持运行 */
    private static final long KEEP_RUNNING = -1;

    /** httpclient对象 */
    private CloseableHttpClient httpclient = null;

    /** 浏览器运行时长,单位:milliseconds*/
    private static long runningTime = 0;

    /**
     * 创建一个模拟浏览器
     * @Date: 16:28 2017/10/24
     * @param time 模拟浏览器运行的时长,单位为milliseconds
     */
    public ClientManager(long time){
        httpclient = HttpClients.createDefault();
        System.out.println("client running ! time:" + time);
        runningTime = time;
        //设置定时关闭
        new Timer().schedule(new CloseTask(), runningTime);

    }

    /**
     * 创建一个模拟浏览器
     * @Date: 16:31 2017/10/24
     * @param time 模拟浏览器运行的时长
     * @param unit 模拟浏览器运行的时长的单位,使用TimeUnit传入
     */
    public ClientManager(long time, TimeUnit unit){
        this(unit.toMillis(time));
    }

    /**
     * 定时关闭器，用于关闭模拟浏览器
     * @Auther: guanhuan_li
     * @Date: 16:41 2017/10/24
     */
    private class CloseTask extends TimerTask{

        @Override
        public void run() {
            System.out.println("Client is close");
        }
    }

    public static void main(String[] args){
        ClientManager clientManager = new ClientManager(5, TimeUnit.SECONDS);
    }
}
