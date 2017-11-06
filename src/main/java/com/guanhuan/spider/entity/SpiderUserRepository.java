package com.guanhuan.spider.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @create 2017-11-06 11:36
 **/
public interface SpiderUserRepository extends CrudRepository<SpiderUser, Long>{
//    @Query("select a from SpiderUser where terrace = ?1 and  status = ?2")
    List<SpiderUser> findAllByTerraceAndStatus(String terrace, int status);
}
