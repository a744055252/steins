package com.guanhuan.inter.jpaimpl;

import com.guanhuan.entity.User;
import com.guanhuan.entity.repository.UserRepository;
import com.guanhuan.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	public void add(User user) {
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public void update(User user) {
		userRepository.save(user);
	}
	
	public boolean isExist(String account) {
		return findByAccount(account) != null;
	}
	
	public User findByAccount(String account) {
		return userRepository.findFirstByAccount(account);
	}
	
	public User findById(long userId) {
		return userRepository.findFirstByUserId(userId);
	}
	
}
