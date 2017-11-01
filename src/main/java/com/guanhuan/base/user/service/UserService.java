package com.guanhuan.base.user.service;

import com.guanhuan.base.user.manager.User;
import com.guanhuan.base.user.manager.UserManager;
import com.guanhuan.base.user.manager.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserService {

	@Autowired
	private UserManager userManager;

	@Autowired
	private UserRepository userRepository;
	
	public void add(User user) {
//		return userManager.add(user);
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
		userManager.delete(user);
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
	}
	
	public User findById(long userId) {
//		User user = null;
//		List<User> userList = userManager.findById(userId);
//		if(null != userList) {
//			user = userList.get(0);
//		}
//		return user;
		return userRepository.findFirstById(userId);
	}
	
	public List<User> getUserList(int begin, int amount){
		return userManager.getUserList(begin, amount);
	}
}
