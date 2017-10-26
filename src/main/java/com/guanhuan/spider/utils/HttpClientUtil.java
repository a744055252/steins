package com.guanhuan.spider.utils;

import com.guanhuan.common.utils.SpiderUtil;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * httpclient工具类
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-26 14:51
 **/
public class HttpClientUtil {

    /** 超时时间 */
    private static final int TIMEOUT = 8 * 1000;

    /**
     * 增加消息头和相关配置
     * @Date: 15:43 2017/10/26
     * @param httpRequestBase
     * @param headPath
     */
    private static void config(HttpRequestBase httpRequestBase, String headPath, int timeout) throws Exception {
        Map<String, String> headMap = null;

        //读取配置文件
        headMap = SpiderUtil.getProperties(headPath);

        //设置消息头
        for(Map.Entry<String, String> map : headMap.entrySet()){
            httpRequestBase.setHeader(map.getKey(), map.getValue());
        }

        //设置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();

        httpRequestBase.setConfig(requestConfig);
    }

    public static void config(HttpRequestBase httpRequestBase, String headPath) throws Exception {
        config(httpRequestBase, headPath, TIMEOUT);
    }



}
