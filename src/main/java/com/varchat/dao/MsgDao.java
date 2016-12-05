package com.varchat.dao;

import com.varchat.model.Apply;
import com.varchat.model.Message;
import com.varchat.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/12/4.
 */
@Repository
public interface MsgDao {

    /**
     * 插入信息
     * @param message
     * @return
     */
    Integer insertMsg(@Param("message")Message message);


    /**
     * 查找信息
     * @param senderId
     * @param receiverId
     * @return
     */
    List<Message> selectAllById(@Param("senderId")int senderId,@Param("receiverId")int receiverId);

    /**
     * 删除信息
     * @param senderId
     * @param receiverId
     * @return
     */
    Integer deleteMsg(@Param("senderId")int senderId,@Param("receiverId")int receiverId);
}
