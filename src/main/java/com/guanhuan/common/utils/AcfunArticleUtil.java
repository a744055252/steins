package com.guanhuan.common.utils;

import com.google.common.collect.Lists;
import com.guanhuan.entity.ACMsg;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class AcfunArticleUtil {

    /** 文章综合消息 */
    private static List<ACMsg> compMsgList;

    /** 文章工作情感消息 */
    private static List<ACMsg> workMsgList;

    /** 文章动漫文化消息 */
    private static List<ACMsg> animeMsgList;

    /** 文章漫画小说消息 */
    private static List<ACMsg> cartoonMsgList;

    /** 文章游戏消息 */
    private static List<ACMsg> gameMsgList;

    /** 香蕉榜 */
    private static List<ACMsg> bananaMsgList;

    static {
        compMsgList = Lists.newArrayList();
        workMsgList = Lists.newArrayList();
        animeMsgList = Lists.newArrayList();
        cartoonMsgList = Lists.newArrayList();
        gameMsgList = Lists.newArrayList();
        bananaMsgList = Lists.newArrayList();
    }

    public static void refresh() throws Exception {
        compMsgList.clear();
        workMsgList.clear();
        animeMsgList.clear();
        cartoonMsgList.clear();
        gameMsgList.clear();
        bananaMsgList.clear();
        init();
    }

    /**
     * @since: 2017/10/22/022 20:50
     **/
    private static void init() throws Exception {

        Document doc = SpiderUtil.getDocument("http://www.acfun.cn/");
        Elements banana = doc.select("div[m-name='香蕉榜'] figure");

        //香蕉榜
        initBanana(banana);

        //文章区
        initArticle(doc, compMsgList, "综合");
        initArticle(doc, workMsgList, "工作情感");
        initArticle(doc, animeMsgList,"动漫文化");
        initArticle(doc, cartoonMsgList,"漫画小说");
        initArticle(doc, gameMsgList,"游戏");

    }

    private static void initArticle(Document doc, List<ACMsg> list, String type){
        Element element = doc.select("div[m-name='"+type+"']>ul").first();
        Elements lis = element.getElementsByTag("li");

        Element a;
        String acUrl,id,title,auther,createTime,click,review;
        String[] temp;

        for(Element li : lis){
            a = li.getElementsByTag("a").first();
            acUrl = "www.acfun.cn/"+a.attr("href");
            id = acUrl.substring(acUrl.lastIndexOf("/"));
            temp = a.attr("title").split("\r");
            title = temp[0];
            auther = temp[1].substring(2);

            if(!temp[2].contains("/") || !temp[2].contains("点击") || !temp[2].contains("评论")){
                continue;
            }

            createTime = temp[2].substring(3, temp[2].indexOf("/")-1);
            click = temp[2].substring(temp[2].indexOf("点击")+3, temp[2].lastIndexOf("/")-1);
            review = temp[2].substring(temp[2].indexOf("评论")+3, temp[2].length()-1);

            ACMsg acMsg = new ACMsg();
            acMsg.setTerraceId(id);
            acMsg.setTitle(title);
            if(!review.equals("")){
                acMsg.setReview(Long.parseLong(review));
            }
            //会有11.7万这样的数据
            if(!click.equals("")) {
                acMsg.setClick((long)NumberUtil.toNumber(click));
            }
            acMsg.setAuther(auther);
            acMsg.setCreateTime(DateUtil.getLongDate(createTime));
            acMsg.setAcUrl(acUrl);
            acMsg.setType(type);

            list.add(acMsg);
        }
    }

    /**
     * 初始化香蕉榜数据
     * @since: 2017/10/22/022 19:57
     **/
    private static void initBanana(Elements elements){
        Element a,img,fig;
        String acUrl,id,imgUrl,title,auther,createTime,click,review;
        String[] temp;

        for(Element element : elements){
            //数据获取
            a = element.getElementsByTag("a").first();
            acUrl = a.attr("href");
            id = a.attr("data-did");
            img = a.child(0);
            imgUrl = img.attr("data-original");
            fig = element.select("figcaption>b>a").first();
            temp = fig.attr("title").split("\r");
            title = temp[0];
//            if(!temp[2].contains("/") || !temp[2].contains("点击:") || !temp[2].contains("评论:")
//                    ||!temp[2].contains("UP:") || !temp[2].contains("发布于")){
//                continue;
//            }
            auther = temp[1].substring(temp[1].indexOf("UP:")+3);
            createTime = temp[2].substring(temp[2].indexOf("发布于")+3, temp[2].indexOf("/")-1);
            click = temp[2].substring(temp[2].indexOf("点击:")+3, temp[2].lastIndexOf("/")-1);
            review = temp[2].substring(temp[2].indexOf("评论:")+3, temp[2].length()-1);

            ACMsg acMsg = new ACMsg();
            acMsg.setAcUrl("www.acfun.cn"+acUrl);
            acMsg.setAuther(auther);
            acMsg.setClick(Long.parseLong(click));
            acMsg.setCreateTime(DateUtil.getLongDate(createTime));
            acMsg.setImageUrl(imgUrl);
            acMsg.setReview(Long.parseLong(review));
            acMsg.setTitle(title);
            acMsg.setType("banana");
            acMsg.setTerraceId(id);

            bananaMsgList.add(acMsg);
        }
    }

    public static List<ACMsg> getCompMsgList() throws Exception {
        if(compMsgList.isEmpty()){
            init();
        }
        return compMsgList;
    }

    public static List<ACMsg> getWorkMsgList() throws Exception {
        if(workMsgList.isEmpty()){
            init();
        }
        return workMsgList;
    }

    public static List<ACMsg> getAnimeMsgList() throws Exception {
        if(animeMsgList.isEmpty()){
            init();
        }
        return animeMsgList;
    }

    public static List<ACMsg> getCartoonMsgList() throws Exception {
        if(cartoonMsgList.isEmpty()){
            init();
        }
        return cartoonMsgList;
    }

    public static List<ACMsg> getGameMsgList() throws Exception {
        if(gameMsgList.isEmpty()){
            init();
        }
        return gameMsgList;
    }

    public static List<ACMsg> getBananaMsgList() throws Exception {
        if(bananaMsgList.isEmpty()){
            init();
        }
        return bananaMsgList;
    }

    public static void main(String[] args) throws Exception {
        AcfunArticleUtil.init();
    }

}
