package com.guanhuan.spider.service;

import com.guanhuan.spider.manager.ACMsg;
import com.guanhuan.spider.manager.ACMsgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ACMsgService")
public class ACMsgService {

    @Autowired
    private ACMsgManager acMsgManager;

    public boolean add(ACMsg acMsg) {
        return acMsgManager.add(acMsg);
    }

    public boolean delete(ACMsg acMsg) {
        return acMsgManager.delete(acMsg);
    }

    public boolean update(ACMsg acMsg) {
        return acMsgManager.update(acMsg);
    }

    public ACMsg findById(long id) {
        ACMsg acMsg = null;
        List<ACMsg> acMsgList = acMsgManager.findById(id);
        if(null != acMsgList) {
            acMsg = acMsgList.get(0);
        }
        return acMsg;
    }

    public List<ACMsg> getMessageList(int begin, int amount){
        return acMsgManager.getMessageList(begin, amount);
    }
}
