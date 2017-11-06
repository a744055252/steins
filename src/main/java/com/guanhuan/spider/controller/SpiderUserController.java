package com.guanhuan.spider.controller;

import com.guanhuan.spider.entity.SpiderUser;
import com.guanhuan.spider.entity.SpiderUserRepository;
import com.guanhuan.spider.service.SpiderUserService;
import org.hibernate.annotations.AttributeAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 爬虫用户
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-06 16:54
 **/
@RestController
@RequestMapping("SpiderUser")
public class SpiderUserController {

    private static final Logger logger = LoggerFactory.getLogger(SpiderUserController.class);

    @Autowired
    private SpiderUserService spiderUserService;

    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView add(){
        return new ModelAndView("spiderUserAdd");
    }

    /**
     * 新增爬虫用户
     * @Date: 17:04 2017/11/6
     * @param user
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String addSpiderUser(@ModelAttribute SpiderUser user){
        spiderUserService.add(user);
        logger.info("添加爬虫账号:"+user.toString());
        return "ok";
    }
}
