package com.guanhuan.inter;

import com.guanhuan.entity.ACMsg;

import java.util.List;

/**
 * @author guanhuan_li
 * @since  2017-11-10 17:24
 **/
public interface ACMsgService {

    boolean add(ACMsg acMsg);

    void delete(ACMsg acMsg);

    void update(ACMsg acMsg);

    void addAll(Iterable<ACMsg> it);

    ACMsg findTop();

    List<ACMsg> findAllDesc(int page, int size);

}
