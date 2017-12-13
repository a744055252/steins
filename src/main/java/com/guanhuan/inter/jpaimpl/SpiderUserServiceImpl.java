package com.guanhuan.inter.jpaimpl;

import com.guanhuan.entity.SpiderUser;
import com.guanhuan.entity.repository.SpiderUserRepository;
import com.guanhuan.inter.SpiderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-06 11:43
 **/
@Service("SpiderUserService")
public class SpiderUserServiceImpl implements SpiderUserService {
    @Autowired
    private SpiderUserRepository spiderUserRepository;

    public void add(SpiderUser user) {
        spiderUserRepository.save(user);
    }

    public void delete(SpiderUser user) {
        spiderUserRepository.delete(user);
    }

    public void update(SpiderUser user) {
        spiderUserRepository.save(user);
    }

    public List<SpiderUser> findAllByTerraceAndStatus(String terrace, int status){
        return spiderUserRepository.findAllByTerraceAndStatus(terrace,status);
    }

}
