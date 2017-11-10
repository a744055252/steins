package com.guanhuan.spider.inter;

import com.guanhuan.spider.entity.SpiderUser;

import java.util.List;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-10 17:24
 **/
public interface SpiderUserService {

    void add(SpiderUser spiderUser);

    void delete(SpiderUser spiderUser);

    void update(SpiderUser spiderUser);

    List<SpiderUser> findAllByTerraceAndStatus(String terrace, int status);
}
