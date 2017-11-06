package com.guanhuan.base.user.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-01 16:26
 **/
public interface UserRepository extends CrudRepository<User, Long> {

//    @Query("select a from User a where a.account = ?1")
    User findFirstByAccount(String account);

//    @Query("select a from User a where a.userId = ?1")
    User findFirstByUserId(long id);
}
