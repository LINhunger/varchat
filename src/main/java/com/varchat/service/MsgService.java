package com.varchat.service;

import com.varchat.dao.MsgDao;
import com.varchat.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hunger on 2016/12/4.
 */
@Service
public class MsgService {

    @Autowired
    private MsgDao msgDao;

   public Integer insertMsg(Message message) {
        return msgDao.insertMsg(message);
    }

    public Integer deleteMsg(int senderId,int receiverId){
        return msgDao.deleteMsg(senderId,receiverId);
    }
    public List<Message> selectAllById(int senderId,int receiverId) {
        return msgDao.selectAllById(senderId,receiverId);
    }
}
