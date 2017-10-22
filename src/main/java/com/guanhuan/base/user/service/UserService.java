package com.guanhuan.base.user.service;

import com.guanhuan.base.user.manager.User;
import com.guanhuan.base.user.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserService {

	@Autowired
	private UserManager userManager;
	
	public boolean add(User user) {
		return userManager.add(user);
	}
	
	public boolean delete(User user) {
		return userManager.delete(user);
	}
	
	public boolean update(User user) {
		return userManager.update(user);
	}
	
	public boolean isExist(String account) {
		return userManager.isExist(account);
	}
	
	public User findByAccount(String account) {
		User user = null;
		List<User> userList = userManager.findByAccount(account);
		if(null != userList && !userList.isEmpty()) {
			user = userList.get(0);
		}
		return user;
	}
	
	public User findById(long userId) {
		User user = null;
		List<User> userList = userManager.findById(userId);
		if(null != userList) {
			user = userList.get(0);
		}
		return user;
	}
	
	public List<User> getUserList(int begin, int amount){
		return userManager.getUserList(begin, amount);
	}
}
