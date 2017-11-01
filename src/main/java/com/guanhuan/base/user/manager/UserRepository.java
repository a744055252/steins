package com.guanhuan.base.user.manager;

import org.springframework.data.repository.CrudRepository;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-01 16:26
 **/
public interface UserRepository extends CrudRepository<User, Long> {
    User findFirstByAccount(String account);
    User findFirstById(long id);
}
