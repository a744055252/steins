package com.guanhuan.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanhuan.common.utils.SpiderUtil;
import com.guanhuan.inter.ACMsgService;
import com.guanhuan.inter.HttpClientService;
import com.guanhuan.inter.Spider;
import com.guanhuan.inter.SpiderUserService;
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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Acfun推送爬取
 *
 * @author liguanhuan_a@163.com
 * @since 2017-10-27 17:05
 **/
@Component
public class AcfunSpider implements Spider{

    /** 模拟浏览器 */
    @Resource(name="client")
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
    private static final String LOGINURL = "http://www.acfun.cn//login.aspx";

    /** 推送连接 在后面加页数实现翻页 */
    private static final String PUSHURL = "http://www.acfun.cn/member/publishContent.aspx?isGroup=0&groupId=-1&pageSize=20&pageNo=";

    /** 签到连接 后面加的是当前时间*/
    private static final String SINGURL = "http://www.acfun.cn/webapi/record/actions/signin?channel=0&date=";

    private static final Logger logger = LoggerFactory.getLogger(AcfunSpider.class);

    /**
     * 登录
     * @since : 15:51 2017/11/10
     */
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

        if(acMsgList != null && !acMsgList.isEmpty()) {
            acMsgList.clear();
        }
        //登录,使用签到来判断是否登录
        if(!sign()){
            login();
        }
        //获取数据
        if(getPush()){
            //将acMsgList顺序颠倒，这样保存进mysql的数据才是正确顺序。
            //实现分批爬取时，需要从新设计。
            Collections.reverse(acMsgList);
//            LinkedList<ACMsg> temp = new LinkedList();
//            int i = 0;
//            for (ACMsg acMsg : acMsgList) {
//                temp.add(acMsg);
//                i++;
//                if (i == acMsgList.size() || i % 10 == 0) {
//                    acMsgService.addAll(temp);
//                    temp.clear();
//                }
//            }
            acMsgService.addAll(acMsgList);
            logger.info("AcfunSpider spider success!");
            return true;
        }else {
            logger.error("AcfunSpider spider fail");
            return false;
        }
    }

    /**
     * 获取推送
     * @since 15:51 2017/11/10
     */
    private boolean getPush() throws IOException {
        boolean end = false;
        //已爬取的最新数据，用于判断是否结束爬取
        //需要修改
        ACMsg topMsg = acMsgService.findTop();

        if(acMsgList == null){
            acMsgList = new ArrayList<ACMsg>();
        }

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

            // 当爬取不到数据时，结束
            if(temp.size() == 0){
                break;
            }

                //应该从列表尾到列表头，这才是正确顺序
//            for(int i = temp.size()-1; i > 0 ; i--){
            for (ACMsg aTemp : temp) {
                //当爬取数据id和上次爬取最后的一个数据一致，或者创建时间更晚。则结束
                if (topMsg.getTerraceId().equals(aTemp.getTerraceId()) ||
                        aTemp.getCreateTime() < topMsg.getCreateTime()) {
                    end = true;
                    break;
                }
                acMsgList.add(aTemp);
            }


        }
        return acMsgList != null || !acMsgList.isEmpty();
    }

    /**
     * 签到
     * @since : 15:50 2017/11/10
     */
    public boolean sign() throws IOException {
        //签到请求
        HttpGet signGet = new HttpGet(SINGURL+System.currentTimeMillis());
        SpiderUtil.config(signGet,"head//acfun");
        CloseableHttpResponse response;
        try {
            response = client.execute(signGet);
        } catch (IOException e) {
            logger.error("登录页面:" + signGet + ", 无法访问");
            return false;
        }
        String temp = "";
        if(response.getStatusLine().getStatusCode() == 200) {
            temp = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        return temp != null && temp.indexOf("200")>0;
    }

    /**
     * 解析内容转换为ACMsg
     * @since : 15:52 2017/11/10
     * @param content 内容
     */
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
        return acMsgList.isEmpty();
    }

    public int size() {
        return acMsgList.size();
    }

    public static void main(String[] args) throws IOException {
    }
}
