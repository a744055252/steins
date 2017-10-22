package com.guanhuan.spider.manager;

import com.guanhuan.common.db.HibernateAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liguanhuan_a@163.com
 * @Description: ACMsgManager
 * @Date: 2017/10/15/015 21:23
 **/
@Component
public class ACMsgManager {

    @Autowired
    private HibernateAccessor accessor;

    public boolean add(ACMsg acMsg){
        accessor.save(acMsg);
        return true;
    }

    public boolean update(ACMsg acMsg){
        accessor.update(acMsg);
        return true;
    }

    public boolean delete(ACMsg acMsg){
        accessor.delete(acMsg);
        return true;
    }

    public List<ACMsg> findById(long id){
        List<ACMsg> messageList =  accessor.find(ACMsg.class,"select * from acMsg where id='"+id+"';");
        return messageList;
    }

    public List<ACMsg> getMessageList(int begin, int amount){
        List<ACMsg> messageList = accessor.find(ACMsg.class,"select * from acMsg limit " + begin + "," + amount+";");
        return messageList;
    }


}
