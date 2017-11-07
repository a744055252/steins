package com.guanhuan.spider.service;

import com.guanhuan.spider.entity.ACMsg;
import com.guanhuan.spider.entity.ACMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ACMsgService")
public class ACMsgService {

    @Autowired
    private ACMsgRepository acMsgRepository;

    public boolean add(ACMsg acMsg) {
        acMsgRepository.save(acMsg);
        return true;
    }

    public void delete(ACMsg acMsg) {
        acMsgRepository.delete(acMsg);
    }

    public void update(ACMsg acMsg) {
        acMsgRepository.save(acMsg);
    }

    public void addAll(Iterable<ACMsg> it){
        acMsgRepository.saveAll(it);
    }

    public ACMsg findTop() {
        return acMsgRepository.findTopByOrderByIdDesc();
    }

}
