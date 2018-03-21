package com.guanhuan.controller;

import com.guanhuan.authorization.annotation.CurrentUser;
import com.guanhuan.authorization.manager.impl.RedisTokenManager;
import com.guanhuan.entity.User;
import com.guanhuan.inter.UserService;
import com.guanhuan.common.utils.CookieUtil;
import com.guanhuan.common.utils.DateUtil;
import com.guanhuan.common.utils.IpUtil;
import com.guanhuan.config.Constants;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.entity.ResultModel;
import com.guanhuan.entity.AcfunSpider;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 * 
 * @author guanhuan-li
 * @since  2017年8月17日
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
	 * @param bindingResult bindingResult
	 * @param user user
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView addUser(@Validated @ModelAttribute User user,
								BindingResult bindingResult) throws Exception {
		logger.debug("add running!");

		if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			List<String> errorList = new ArrayList<String>();
			String temp;
			for(ObjectError error : allErrors){
				temp = error.getDefaultMessage();
				if (temp != null) {
					errorList.add(new String(temp.getBytes("ISO-8859-1"),"UTF-8"));
				}
			}
			ModelAndView mav = new ModelAndView("formatError");
			mav.addObject("errorList", errorList);
			return mav;
		}

		//检查账号是否已经存在
		ModelAndView mav;
		User tempUser = userService.findByAccount(user.getAccount());
		if(null != tempUser && 0 != tempUser.getUserId()) {
			mav = new ModelAndView("formatError");
			mav.addObject("errorMessage", "账号："+user.getAccount()+"已经存在，请重新输入");
			return mav;
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
	 * @author liguanhuan_a@163.com
	 * @since : 2017/10/14/014 20:39
	 **/
	@RequestMapping(value="/token", method=RequestMethod.POST)
	public ResultModel<?> login(HttpServletResponse response, @RequestParam("account") String account,
											 @RequestParam("password") String password) {
		User user = userService.findByAccount(account);
		if(user == null){
			return ResultModel.error(ResultStatus.USER_NOT_FOUND);
		}
		if(!BCrypt.checkpw(password, user.getPassword())){
			return ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
		}
		//成功登陆,创建token
		String token = redisTokenManager.createToken(user);
		//将token存入header和cookie
		response.addHeader(Constants.AUTHORIZATION, token);
		CookieUtil.addCookie(response, Constants.AUTHORIZATION, token, 3, TimeUnit.DAYS);

		return ResultModel.ok(token);
	}


	/**
	 * 通过userId查找用户
	 * 
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel<?> getUser(@CurrentUser User user) {
		if(user != null){
			user.setPassword("");
			return ResultModel.ok(user);
		}
		return ResultModel.error(ResultStatus.USER_NOT_LOGIN);
	}

	 /**
	  * 跳转注册页面
	  * @author liguanhuan_a@163.com
	  * @since 2017/10/15/015 9:00
	  **/
	@RequestMapping("/register")
	public ModelAndView register(){
		return new ModelAndView("register");
	}


	/**
	 * 通过account查询用户是否已经存在
	 * 
	 * @param session session
	 * @param request request
	 * @param account account
	 * @return boolean
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
		logger.info(currentUser.getAccount()+"_"+currentUser.getUserName()+" ip="+ip+"查询了账号：["+account+"]是否存在");
		User user = userService.findByAccount(account);
		return null != user && user.getUserId() != 0;
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

	@RequestMapping("/exception")
	public void exception(){
		throw new RuntimeException("运行时异常测试");
	}

}
