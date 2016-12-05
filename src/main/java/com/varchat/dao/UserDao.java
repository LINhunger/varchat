package com.varchat.dao;


import com.varchat.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface UserDao {

    /**
     * 插入用户
     * @param user 用户对象
     * @return 成功为1，错误为0
     */
    int insertUser(@Param("user") User user);

    /**
     * 更新用户
     * @param user 用户对象
     * @return 成功为1，错误为0
     */
    int updateUser(@Param("user") User user);

    /**
     * 根据id查找用户
     * @param userId 用户id
     * @return 用户对象
     */
    User selectOneById(@Param("userId") int userId);

    /**
     * 根据邮箱查找用户
     * @param userName 用户
     * @return 用户对象
     */
    User selectOneByName(@Param("userName") String userName);

}
