package com.guanhuan.spider.utils;/**
 * @author
 * @create 2017-10-24 14:58
 **/

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-24 14:58
 **/
public class HttpClientDemo {
    public static void main(String[] args) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String pushUrl = "http://www.acfun.cn/member/publishContent.aspx?isGroup=0&groupId=-1&pageSize=10&pageNo=1";

        //登陆页面
        HttpGet httpGet = new HttpGet("http://www.acfun.cn/login/");
        //推送页面
        HttpGet push = new HttpGet(pushUrl);

        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.acfun.cn//login.aspx");

        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("username", "744055252@qq.com"));
        parameters.add(new BasicNameValuePair("password", "845650111"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = null;
        CloseableHttpResponse response = null;
        try {
            httpclient.execute(httpGet);

            formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
            // 伪装浏览器请求

            for(Map.Entry<String, String> entry : AcfunUtil.getProperties().entrySet()){
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }

            Header[] headers = httpPost.getAllHeaders();
            StringBuilder sb = new StringBuilder();
            for(Header h : headers){
                sb.append(h.toString()+"\r\n");
            }
            System.out.println(sb.toString());
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取服务端响应的数据
                String content = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(content);
            }

            //获取推送页面
            response = httpclient.execute(push);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取服务端响应的数据
                String content = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(content);
            }

        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
