package com.guanhuan.spider.service;

import com.guanhuan.spider.entity.ACMsg;
import com.guanhuan.spider.entity.ACMsgRepository;
import com.guanhuan.spider.inter.ACMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ACMsgService")
public class ACMsgServiceImpl implements ACMsgService{

    private final ACMsgRepository acMsgRepository;

    @Autowired
    public ACMsgServiceImpl(ACMsgRepository acMsgRepository) {
        this.acMsgRepository = acMsgRepository;
    }

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

    public List<ACMsg> findAllDesc(int page, int size){
        return acMsgRepository.findByOrderByIdDesc(PageRequest.of(page, size));
    }

}
