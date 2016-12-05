package com.varchat.dao;

import com.varchat.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/12/4.
 */
@Repository
public interface FriendDao {
    /**
     * 插入好友关系
     * @param userId
     * @param friendId
     * @return
     */
    Integer insertFriend(@Param("userId") int userId, @Param("friendId") int friendId);

    /**
     * 删除好友关系
     * @param userId
     * @param friendId
     * @return
     */
    Integer deleteFriend(@Param("userId") int userId,@Param("friendId") int friendId);

    /**
     * 查找用户的全部好友
     * @param userId
     * @return
     */
    List<User> selectFriendByUserId(@Param("userId")int userId);

    /**
     * 查找指定好友
     * @param userId
     * @param friendId
     * @return
     */
    User selectFriendByFriendId(@Param("userId") int userId,@Param("friendId") int friendId);
}
