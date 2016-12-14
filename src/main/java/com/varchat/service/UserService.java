package com.varchat.service;

import com.varchat.dao.FriendDao;
import com.varchat.dao.UserDao;
import com.varchat.dto.RequestResult;
import com.varchat.enums.StatEnum;
import com.varchat.exception.user.InformationFormatterFault;
import com.varchat.exception.user.LoginMatchException;
import com.varchat.exception.user.LoginNotExitUserException;
import com.varchat.exception.user.RegisterFormatterFaultException;
import com.varchat.model.User;
import com.varchat.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.varchat.websocket.MyWebSocketHandler.userSocketSessionMap;

/**
 * Created by hunger on 2016/12/4.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendDao friendDao;

    /**
     * 注册用户
     * @param user 用户对象
     * @return 包含用户名的dto对象
     */
    public RequestResult<User> register(User user) {
        if (user == null) {
            throw new RuntimeException("空用户对象");
        }
        else if(
                        !user.getUserName().matches("[a-z0-9A-Z\\u4e00-\\u9fa5]{1,15}") ||
                        !user.getPassword().matches("\\w{1,15}")
                ) {
            throw new RegisterFormatterFaultException("注册信息格式错误");
        }else {
            //加密密码项
            user.setPassword(Encryption.getMD5(user.getPassword()));
            userDao.insertUser(user);
            User dbUser = userDao.selectOneById(user.getUserId());
            return new RequestResult<User>(StatEnum.REGISTER_SUCESS,dbUser);
        }
    }


    /**
     * 用户登录
     * @param userName 用户邮箱
     * @param password 密码
     * @return
     */
    public RequestResult<User> login(String userName , String password) {
        User dbUser = userDao.selectOneByName(userName);
        if (dbUser == null) {
            //不存在的用户
            throw new LoginNotExitUserException("不存在的用户");
        }else if (!dbUser.getPassword().equals(
                Encryption.getMD5(password)
        )){
            //用户名或密码错误
            throw new LoginMatchException("错误的用户名或密码");
        }else {
            //登录成功
            dbUser.setIsOnline(1);
            //检索用户的所有好友
            List<User> friends = selectFriends(dbUser.getUserId());
            dbUser.setFriends(friends);
            userDao.updateUser(dbUser);
            return new RequestResult<User>(StatEnum.LOGIN_SUCCESS,dbUser);
        }
    }

    /**
     * 修改用户信息方法
     * @param user 用户对象
     * @return dto对象
     */
    public RequestResult<User> updateUser(User user) {

        if (userDao.selectOneByName(user.getUserName())!=null) {
            throw new RuntimeException("该用户已存在");
        }
        else if(
                        !user.getUserName().matches("[a-z0-9A-Z\\u4e00-\\u9fa5]{1,15}") //TODO
                ) {
            throw new InformationFormatterFault("修改信息格式错误");
        }else {
            //更新用户对象
            user.setPassword("");
            userDao.updateUser(user);
            //查找更新后的对象
            User dbUser = userDao.selectOneById(user.getUserId());
            //检索用户的所有好友
            List<User> friends = friendDao.selectFriendByUserId(dbUser.getUserId());
            dbUser.setFriends(friends);
            return new RequestResult<User>(StatEnum.INFORMATION_CHANGE_SUCCESS,dbUser);
        }
    }

    /**
     * 查找用户好友
     * @param userId
     * @return
     */
    public List<User> selectFriends(Integer userId) {
        List<User> list = friendDao.selectFriendByUserId(userId);
        List<User> friends = new ArrayList<User>();
        for (User f:list) {
            if (f.getIsOnline() == 1) {
                friends.add(f);
            }
        }
        for (User f:list) {
            if (f.getIsOnline() == 0) {
                friends.add(f);
            }
        }
        return friends;
    }

    /**
     * 登出
     * @param userId
     * @return
     */
    public Integer signout(Integer userId) {
        User user = new User();
        user.setIsOnline(0);
        user.setUserId(userId);
        return  userDao.updateUser(user);
    }

}
