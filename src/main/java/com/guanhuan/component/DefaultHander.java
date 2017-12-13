package com.guanhuan.component;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

@Component
class DefaultHander implements HttpRequestRetryHandler {
        public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
            if (i >= 5) {// 如果已经重试了5次，就放弃
                return false;
            }
            if (e instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (e instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (e instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (e instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (e instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (e instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext
                    .adapt(httpContext);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    }