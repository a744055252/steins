package com.guanhuan.inter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

/**
 * httpclient服务
 * @author guanhuan_li
 * @since  2017-11-10 17:23
 **/
public interface HttpClientService {
    void release();
    void destroy() throws Exception;
    /** 执行请求 */
    CloseableHttpResponse execute(HttpRequestBase request) throws IOException;

}
