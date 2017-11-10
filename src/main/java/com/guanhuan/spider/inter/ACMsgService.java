package com.guanhuan.spider.inter;

import com.guanhuan.spider.entity.ACMsg;

import java.util.List;

/**
 *
 * @author
 * @create 2017-11-10 17:24
 **/
public interface ACMsgService {

    boolean add(ACMsg acMsg);

    void delete(ACMsg acMsg);

    void update(ACMsg acMsg);

    void addAll(Iterable<ACMsg> it);

    ACMsg findTop();

    List<ACMsg> findAllDesc(int page, int size);

}
