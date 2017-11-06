package com.guanhuan.base.user.service;

import com.guanhuan.base.user.entity.User;
import com.guanhuan.base.user.entity.UserManager;
import com.guanhuan.base.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserService {

//	@Autowired
//	private UserManager userManager;

	@Autowired
	private UserRepository userRepository;
	
	public void add(User user) {
//		return userManager.add(user);
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
//		userManager.delete(user);
	}
	
	public void update(User user) {
//		return userManager.update(user);
		userRepository.save(user);
	}
	
	public boolean isExist(String account) {
//		return userManager.isExist(account);
		return findByAccount(account) != null;
	}
	
	public User findByAccount(String account) {
//		User user = null;
//		List<User> userList = userManager.findByAccount(account);
//		if(null != userList && !userList.isEmpty()) {
//			user = userList.get(0);
//		}
//		return user;
		return userRepository.findFirstByAccount(account);
//		return null;
	}
	
	public User findById(long userId) {
//		User user = null;
//		List<User> userList = userManager.findById(userId);
//		if(null != userList) {
//			user = userList.get(0);
//		}
//		return user;
		return userRepository.findFirstByUserId(userId);
//		return null;
	}
	
}
