package com.guanhuan.spider.controller;

import com.guanhuan.spider.entity.ACMsg;
import com.guanhuan.spider.inter.ACMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取acfun消息
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-10 17:41
 **/
@RestController
@RequestMapping("/ACMsg")
public class ACMsgController {

    private static final Logger logger = LoggerFactory.getLogger(ACMsgController.class);

    @Autowired
    private ACMsgService service;

    @RequestMapping("/acmsg/{page}/{size}")
    @ResponseBody
    public List<ACMsg> getACMsg(@PathVariable int page, @PathVariable int size){
        return service.findAllDesc(page,size);
    }
}
