package com.guanhuan.spider.inter.impl;

import com.guanhuan.common.utils.SpiderUtil;
import com.guanhuan.spider.inter.Spider;
import com.guanhuan.spider.manager.SpiderUser;
import com.guanhuan.spider.service.HttpClientService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acfun推送爬取
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-27 17:05
 **/
public class AcfunSpider implements Spider{

    /** 模拟浏览器 */
    @Autowired
    private HttpClientService client;

    /** 用于登录的账号 */
    private List<SpiderUser> loginUser;

    /** 所属的平台 */
    private static final String TERRACE = Spider.TERRACE_ACFUN;

    /** 登录的post请求url */
    private static final String LOGINURL = "http://www.acfun.cn//login.aspx";

    private static final Logger logger = LoggerFactory.getLogger(AcfunSpider.class);

    public boolean login() throws IOException {
        if(loginUser == null || loginUser.isEmpty()){
            logger.error("平台:" + TERRACE+" ,的账号为空");
            return false;
        } else {
            //登录前访问一次登录页面
            String tempUrl = "http://www.acfun.cn/login/";
            HttpGet temp = new HttpGet(tempUrl);
            try {
                client.execute(temp);
            } catch (IOException e){
                logger.error("登录页面:"+tempUrl+", 无法访问");
                throw new RuntimeException("登录页面:"+tempUrl+", 无法访问", e);
            }
            //用loginUser里保存用户进行登录
            for(SpiderUser user : loginUser){
                HttpPost login = new HttpPost(LOGINURL);
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("username", user.getAccount()));
                parameters.add(new BasicNameValuePair("password", user.getPassword()));
                // 构造一个form表单式的实体
                UrlEncodedFormEntity formEntity = null;
                CloseableHttpResponse response = null;
                formEntity = new UrlEncodedFormEntity(parameters);
                // 将请求实体设置到httpPost对象中
                login.setEntity(formEntity);
                // 伪装浏览器请求
                SpiderUtil.config(login,"head//acfun");
            }
        }
        return true;
    }

    public boolean running() {
        return false;
    }

    public String getTerrace() {
        return TERRACE;
    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }
}
