package com.guanhuan.base.user.manager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-01 16:26
 **/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
//    User findFirstByAccount(String account);
//    User findFirstById(long id);
    User findFirstByAccount(String account);
    User findFirstById(long id);
}
