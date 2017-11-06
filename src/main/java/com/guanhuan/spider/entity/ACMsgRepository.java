package com.guanhuan.spider.entity;

import com.guanhuan.common.db.HibernateAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: ACMsgManager
 * @Date: 2017/10/15/015 21:23
 **/
public interface ACMsgRepository extends CrudRepository<ACMsg, Long>{

}
