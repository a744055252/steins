package com.guanhuan.entity.repository;

import com.guanhuan.entity.ACMsg;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: ACMsgManager
 * @Date: 2017/10/15/015 21:23
 **/
public interface ACMsgRepository extends JpaRepository<ACMsg, Long> {
    ACMsg findTopByOrderByIdDesc();
    List<ACMsg> findByOrderByIdDesc(Pageable pageable);
}
