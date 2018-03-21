package com.guanhuan.controller;

import com.guanhuan.common.utils.AcfunArticleUtil;
import com.guanhuan.entity.ACMsg;
import com.guanhuan.entity.AcfunSpider;
import com.guanhuan.inter.ACMsgService;
import com.guanhuan.entity.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取acfun消息
 *
 * @author liguanhuan_a@163.com
 * @since  2017-11-10 17:41
 **/
@RestController
@RequestMapping("/ACMsg")
public class ACMsgController {

    @Autowired
    private ACMsgService service;

    @Autowired
    private AcfunSpider acfunSpider;

    /**
     * 获取已经登录的acfun用户的推送信息
     * @since 11:17 2017/11/13
     * @param page 页数，0为第一页
     * @param size 一页的大小，包含多少条推送数据
     */
    @RequestMapping(value = "/acmsg/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getACMsg(@PathVariable int page, @PathVariable int size) throws IOException {
        acfunSpider.running();
        return ResultModel.ok(service.findAllDesc(page,size));
    }

    /**
     * 获取文章综合消息
     * @since 11:18 2017/11/13
     */
    @RequestMapping(value = "/comp", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getComp(boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getCompMsgList());
    }

    /**
     * 获取文章工作情感消息
     * @since : 11:18 2017/11/13
     */
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getWork(boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getWorkMsgList());
    }

    /**
     * 获取文章动漫文化消息
     * @since 11:18 2017/11/13
     */
    @RequestMapping(value = "/anime", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getAnime(boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getAnimeMsgList());
    }

    /**
     * 获取漫画小说消息
     * @since 11:18 2017/11/13
     */
    @RequestMapping(value = "/cartoon", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getCartoon(boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getCartoonMsgList());
    }

    /**
     * 获取文章游戏消息
     * @since 11:18 2017/11/13
     */
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getGame(boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getGameMsgList());
    }

    /**
     * 获取香蕉榜消息
     * @since 11:18 2017/11/13
     */
    @RequestMapping(value = "/banana/{refresh}", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<List<ACMsg>> getBanana(@PathVariable boolean refresh) throws Exception {
        if(refresh) {
            AcfunArticleUtil.refresh();
        }
        return ResultModel.ok(AcfunArticleUtil.getBananaMsgList());
    }

    @RequestMapping(value = "/allAritcle", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel<Map<String, List<ACMsg>>> getAllAritcle() throws Exception {
        AcfunArticleUtil.refresh();
        Map<String, List<ACMsg>> map = new HashMap<String, List<ACMsg>>();
        map.put("comp", AcfunArticleUtil.getCompMsgList());
        map.put("work", AcfunArticleUtil.getWorkMsgList());
        map.put("anime", AcfunArticleUtil.getAnimeMsgList());
        map.put("cartoon", AcfunArticleUtil.getCartoonMsgList());
        map.put("game", AcfunArticleUtil.getGameMsgList());
        map.put("banana", AcfunArticleUtil.getBananaMsgList());
        return ResultModel.ok(map);
    }

}
