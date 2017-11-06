package com.guanhuan.base.user.entity;

import com.guanhuan.common.db.HibernateAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * userManager
 * 
 * @author guanhuan-li
 * @email liguanhuan_a@163.com
 * @date 2017年8月16日
 *
 */
@Repository
public class UserManager {

	@Autowired
	private HibernateAccessor accessor;
	
	public boolean add(User user){
		accessor.save(user);
		return true;
	}
	
	public boolean update(User user){
		accessor.update(user);
		return true;
	}
	
	public boolean delete(User user){
		accessor.delete(user);
		return true;
	}
	
	public List<User> findByAccount(String account){
		List<User> userList =  accessor.find(User.class,"select * from user where account='"+account+"';");
		return userList;
	}
	
	public List<User> findById(long userId){
		List<User> userList =  accessor.find(User.class,"select * from user where userId='"+userId+"';");
		return userList;
	}
	
	public List<User> getUserList(int begin, int amount){
		List<User> userList = accessor.find(User.class,"select * from user limit " + begin + "," + amount+";");
		return userList;
	}
	
	public boolean isExist(String account){
		return !this.findByAccount(account).isEmpty();
	}
}
