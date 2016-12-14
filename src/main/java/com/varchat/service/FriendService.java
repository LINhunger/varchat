package com.varchat.service;

import com.varchat.dao.ApplyDao;
import com.varchat.dao.FriendDao;
import com.varchat.dao.UserDao;
import com.varchat.dto.RequestResult;
import com.varchat.enums.StatEnum;
import com.varchat.model.Apply;
import com.varchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hunger on 2016/12/4.
 */
@Service
public class FriendService {

    @Autowired
    private UserDao  userDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private ApplyDao  applyDao;

    /**
     * 插入申请对象
     * @param apply 申请对象
     * @return 成功为1，失败为0
     */
    public RequestResult<?> insertApply(Apply apply){

        List<Apply> applies = applyDao.selectAllByUserId(apply.getReceiverId());
        for(Apply a:applies) {
            if (a.getSender().getUserId()==apply.getSender().getUserId()
                    &&a.getStatus()==0) {
                return new RequestResult<String>(StatEnum.APPLY_ALREADY_EXIST);
            }
        }
        List<User> friends = friendDao.selectFriendByUserId(apply.getSender().getUserId());
        for (User f:friends){
            if (apply.getReceiverId()==f.getUserId()){
                return new RequestResult<String>(StatEnum.APPLY_IS_PASS);
            }
        }
        if (1 != applyDao.insertApply(apply)){
            //发送申请不成功
            return new RequestResult<String>(StatEnum.APPLY_SEND_FAIL);
        }
        //发送申请成功
        return new RequestResult<String>(StatEnum.APPLY_SEND_SUCCESS);
    }
    /**
     * 处理申请，修改status
     * @param apply 申请对象
     * @return 成功返回1，失败返回0(包括申请不存在，已经处理过的申请返回），若操作有误返回2
     */
    @Transactional
    public int updateApply(Apply apply){
        //更新时不能将申请设置为未处理状态
        if(0 == apply.getStatus()) {
            return 2;
        }
        if (null==friendDao.selectFriendByFriendId(apply.getSender().getUserId(),apply.getReceiverId())) {
            friendDao.insertFriend(apply.getSender().getUserId(), apply.getReceiverId());
            friendDao.insertFriend(apply.getReceiverId(), apply.getSender().getUserId());
        }
        return applyDao.updateApply(apply);
    }

    /**
     * 查看未处理信息
     * @param userId
     * @return
     */
    public List<Apply> selectAllByUserId(int userId) {
        List<Apply> applyList = applyDao.selectAllByUserId(userId);
        return applyList;
    }

    /**
     * 删除申请
     * @param applyId
     * @return
     */
    public int deleteByApplyId(int applyId) {
        return applyDao.deleteByApplyId(applyId);
    }

    /**
     * 查看用户
     * @param userId
     * @return
     */
    public User selectUser(Integer userId) {
        return userDao.selectOneById(userId);
    }

    /**
     * 搜索用户
     * @param userName
     * @return
     */
    public List<User> searchUser(String userName) {
        return userDao.searchUserByName("%"+userName+"%");
    }
}
