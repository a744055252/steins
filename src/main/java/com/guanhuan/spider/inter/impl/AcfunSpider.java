package com.guanhuan.spider.inter.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanhuan.common.utils.DateUtil;
import com.guanhuan.common.utils.SpiderUtil;
import com.guanhuan.spider.entity.ACMsg;
import com.guanhuan.spider.inter.Spider;
import com.guanhuan.spider.entity.SpiderUser;
import com.guanhuan.spider.service.ACMsgService;
import com.guanhuan.spider.service.HttpClientService;
import com.guanhuan.spider.service.SpiderUserService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Acfun推送爬取
 *
 * @author liguanhuan_a@163.com
 * @create 2017-10-27 17:05
 **/
@Component
public class AcfunSpider implements Spider{

    /** 模拟浏览器 */
    @Autowired
    private HttpClientService client;

    /** 登录账号 */
    @Autowired
    private SpiderUserService spiderUserService;

    /** 消息服务 */
    @Autowired
    private ACMsgService acMsgService;

    /** 用于登录的账号 */
    private List<SpiderUser> loginUser;

    /** 爬取信息列表 */
    private static List<ACMsg> acMsgList;

    /** 所属的平台 */
    private static final String TERRACE = Spider.TERRACE_ACFUN;

    /** 登录的post请求url */
    public static final String LOGINURL = "http://www.acfun.cn//login.aspx";

    /** 推送连接 在后面加页数实现翻页 */
    public static final String PUSHURL = "http://www.acfun.cn/member/publishContent.aspx?isGroup=0&groupId=-1&pageSize=20&pageNo=";

    private static final Logger logger = LoggerFactory.getLogger(AcfunSpider.class);

    static{
        acMsgList = new ArrayList<ACMsg>();
    }

    public boolean login() throws IOException {
        if(loginUser == null || loginUser.isEmpty()){
            //获取登录用到的账号
            loginUser = spiderUserService.findAllByTerraceAndStatus(TERRACE_ACFUN, STATE_EFFECTIVE);
            if(loginUser == null || loginUser.isEmpty()) {
                logger.error("平台:" + TERRACE+" ,的账号为空");
                return false;
            }
        }

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
        HttpPost login = new HttpPost(LOGINURL);
        // 伪装浏览器请求
        SpiderUtil.config(login,"head//acfun");

        for(SpiderUser user : loginUser){
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", user.getAccount()));
            parameters.add(new BasicNameValuePair("password", user.getPassword()));
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity;
            CloseableHttpResponse response;
            formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            login.setEntity(formEntity);
            //登录
            response = client.execute(login);

            //判断登录结果
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
            else {
                logger.error("无法登录:"+user.toString()+
                        "_错误代码:"+response.getStatusLine().getStatusCode());
                return false;
            }
        }
        return false;
    }

    public boolean running() throws IOException {
        logger.info("AcfunSpider running");

        acMsgList.clear();
        //登录
        login();
        //获取数据
        if(getPush()){
            acMsgService.addAll(acMsgList);
            logger.info("AcfunSpider spider success!");
            return true;
        }else {
            logger.error("AcfunSpider spider fail");
            return false;
        }
    }

    private boolean getPush() throws IOException {
        boolean end = false;
        //已爬取的最新数据，用于判断是否结束爬取
        ACMsg topMsg = acMsgService.findTop();

        //页数
        int number = 0;
        //爬取的列表
        List<ACMsg> temp;

        while(!end) {
            //推送页面
            ++number;
            String currentPage = PUSHURL + number;
            HttpGet push = new HttpGet(currentPage);
            CloseableHttpResponse response;
            try {
                response = client.execute(push);
            } catch (IOException e) {
                logger.error("登录页面:" + currentPage + ", 无法访问");
                throw new RuntimeException("登录页面:" + currentPage + ", 无法访问", e);
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                temp = stringToACMsg(EntityUtils.toString(response.getEntity(), "UTF-8"));
            } else {
                logger.error("无法获取推送消息，错误代码:" + response.getStatusLine().getStatusCode());
                throw new RuntimeException("无法获取推送消息，错误代码:" + response.getStatusLine().getStatusCode());
            }


            for(int i = 0; i < temp.size(); i++){
                //当爬取数据id和上次爬取最后的一个数据一致，或者创建时间更晚。则结束
                if(topMsg.getTerraceId().equals(temp.get(i).getTerraceId()) ||
                        temp.get(i).getCreateTime() < topMsg.getCreateTime()){
                    end = true;
                    break;
                }
                acMsgList.add(temp.get(i));
            }
        }
        return acMsgList != null || !acMsgList.isEmpty();
    }

    private List<ACMsg> stringToACMsg(String content){
        JSONObject o = (JSONObject)JSONObject.parse(content);
        JSONArray o1 = (JSONArray) o.get("contents");
        JSONObject temp;
        ACMsg acMsg;
        List<ACMsg> acMsgs = new ArrayList<ACMsg>();
        for(Object o2 : o1){
            acMsg = new ACMsg();
            temp = (JSONObject)o2;
            acMsg.setAcUrl("http://www.acfun.cn"+temp.getString("url"));
            acMsg.setAuther(temp.getString("username"));
            acMsg.setClick(temp.getInteger("views"));
            acMsg.setCreateTime(temp.getLong("releaseDate"));
            acMsg.setReview(temp.getInteger("comments"));
            acMsg.setTerraceId(temp.getString("vid"));
            acMsg.setTitle(temp.getString("title"));
            acMsg.setType(temp.getInteger("isArticle") == 1 ? "文章," : "视频,"+
                    temp.getString("tags"));
            acMsg.setSpiderTime(System.currentTimeMillis());
            acMsg.setImageUrl(temp.getString("titleImg"));
            acMsg.setDescription(temp.getString("description"));
            acMsg.setAutherId(temp.getString("userId"));
            acMsgs.add(acMsg);
        }
        return acMsgs;
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

    public static void main(String[] args) throws IOException {
    }
}
