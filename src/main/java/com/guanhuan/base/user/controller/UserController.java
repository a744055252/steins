package com.guanhuan.base.user.controller;

import com.guanhuan.authorization.annotation.CurrentUser;
import com.guanhuan.authorization.manager.impl.RedisTokenManager;
import com.guanhuan.base.user.entity.User;
import com.guanhuan.base.user.service.UserService;
import com.guanhuan.common.utils.DateUtil;
import com.guanhuan.common.utils.IpUtil;
import com.guanhuan.spider.inter.impl.AcfunSpider;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * 
 * @author guanhuan-li
 * @email liguanhuan_a@163.com
 * @date 2017年8月17日
 *
 */
@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired
	private AcfunSpider acfunSpider;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RedisTokenManager redisTokenManager;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * 添加用户
	 * 
	 * @param bindingResult
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView addUser(@Validated @ModelAttribute User user, BindingResult bindingResult) throws Exception {
		logger.debug("add running!");

		if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			List<String> errorList = new ArrayList<String>();
			for(ObjectError error : allErrors){
				errorList.add(new String(error.getDefaultMessage().getBytes("ISO-8859-1"),"UTF-8"));
			}
			ModelAndView mav = new ModelAndView("formatError");
			mav.addObject("errorList", errorList);
			return mav;
		}

		//检查账号是否已经存在
		ModelAndView mav = null;
		User tempUser = userService.findByAccount(user.getAccount());
		if(null != tempUser && 0 != tempUser.getUserId()) {
			mav.addObject("errorMessage", "账号："+user.getAccount()+"已经存在，请重新输入");
			return new ModelAndView();
		}
		user.setUserId(System.currentTimeMillis());
		String currentTime = DateUtil.getCurrentYMDHMSDate();
		user.setCreateTime(currentTime);
		user.setUpdateTime(currentTime);
		//用户密码加密
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		userService.add(user);
		logger.info("新增用户"+user.toString());
		return new ModelAndView("login");
	}

	/**
	 * @Author: liguanhuan_a@163.com
	 * @param: [session, account, password]
	 * @Description:
	 * @Date: 2017/10/14/014 20:39
	 **/
	@RequestMapping(value="/token", method=RequestMethod.POST)
	public ModelAndView login(@RequestHeader HttpHeaders headers, @RequestParam("account") String account,
							  @RequestParam("password") String password) {
		User user = userService.findByAccount(account);
		if(user == null){
			return new ModelAndView("404")
					.addObject("message","账号不存在！");
		}
		if(!BCrypt.checkpw(password, user.getPassword())){
			logger.info(account+" 输入错误的密码:" + password);
			return new ModelAndView("404")
					.addObject("message","密码错误！");
		}
		//成功登陆,创建token
		String token = redisTokenManager.createToken(user);

		return new ModelAndView("success")
				.addObject("message",token);
	}
	
	/**
	 * 通过userId查找用户
	 * 
	 * @param session
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(HttpSession session, @PathVariable long userId) {
		//当前用户
		User loginUser = (User)session.getAttribute("LoginUser");
		if(loginUser == null) {
			return null;
		}
		User user = userService.findById(userId);
		logger.info("用户："+loginUser.toString()+"查询了用户："+user.toString());
		return user;
	}

	 /**
	  * @Author: liguanhuan_a@163.com
	  * @param:
	  * @Description: 跳转注册页面
	  * @Date: 2017/10/15/015 9:00
	  **/
	@RequestMapping("/register")
	public ModelAndView register(){
		return new ModelAndView("register");
	}


	/**
	 * 通过account查询用户是否已经存在
	 * 
	 * @param session
	 * @param request
	 * @param account
	 * @return
	 */
	@RequestMapping("/isExist/{account}")
	public boolean isExist(HttpSession session, @CurrentUser User currentUser, HttpServletRequest request, @PathVariable String account) {
		String ip = null;
		try {
			ip = IpUtil.getIpAddr(request);
		} catch (Exception e) {
			logger.info("无法获取到IP地址",e);
			session.setAttribute("errorMessage", "无法获取到ip地址");
		}
		logger.info(currentUser.getAccount()+"_"+currentUser.getUserName()+" 查询了账号：["+account+"]是否存在");
		User user = userService.findByAccount(account);
		if(null == user || user.getUserId() == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	@RequestMapping(value = "/redis", method = RequestMethod.POST)
	@ResponseBody
	public String addValue(@RequestParam("key") String key, @RequestParam("value") String value){
		redisTemplate.boundValueOps(key).set(value);
		return key + " : " + redisTemplate.boundValueOps(key).get();
	}

	@RequestMapping("/running")
	@ResponseBody
	public String spider() throws IOException {
		acfunSpider.running();
		return "ok";
	}
	
}
