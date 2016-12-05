package com.varchat.web;

import com.varchat.dto.RequestResult;
import com.varchat.enums.StatEnum;
import com.varchat.exception.user.InformationFormatterFault;
import com.varchat.exception.user.LoginMatchException;
import com.varchat.exception.user.LoginNotExitUserException;
import com.varchat.exception.user.RegisterFormatterFaultException;
import com.varchat.model.User;
import com.varchat.service.UserService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * Created by hunger on 2016/12/4.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param request 请求对象
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<User> signIn(HttpServletRequest request, HttpServletResponse response,
                                      @RequestBody User user) {
        try {
            RequestResult<User> result = userService.login(user.getUserName(),user.getPassword());
            //将用户存到session中
            user = result.getData();
            request.getSession().setAttribute("user",user);
            return result;
        }catch (LoginNotExitUserException e) {
            logger.warn("not exit user.\t");
            return  new RequestResult<User>(StatEnum.LOGIN_NOT_EXIT_USER,null);
        }catch (LoginMatchException e) {
            logger.warn("Incorrect username or password.\t");
            return  new RequestResult<User>(StatEnum.LOGIN_USER_MISMATCH,null);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }


    /**
     * 注册
     * @param request
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> register(HttpServletRequest request,HttpServletResponse response,
                                     @RequestBody User user
                                     ) {

        //注册用户
        try {
            RequestResult<User> result = userService.register(user);
            request.getSession().setAttribute("user",result.getData());
            return result;
        }catch (RegisterFormatterFaultException e) {
            logger.warn("wrong formatter.\t");
            return  new RequestResult<String>(StatEnum.REGISTER_FAMMTER_FAULT);
        }catch (DataIntegrityViolationException e) {//主键重复异常
            logger.warn("user is already exist.\t");
            return  new RequestResult<String>(StatEnum.REGISTER_ALREADY_EXIST);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<String>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 修改用户信息
     * @param request 请求对象
     * @param response 回复对象
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public RequestResult<User> updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                                          @RequestParam(value = "userName", required = false) String userName,
                                          HttpServletRequest request,HttpServletResponse response
    ){
        try {
            User user = (User)request.getSession().getAttribute("user");
            user.setUserName(userName);
            user.setPicture(((User)request.getSession().getAttribute("user")).getUserId()+".jpg");
            RequestResult<User> result = userService.updateUser(user);
            //更新session中的数据
            request.getSession().setAttribute("user",result.getData());
            //更改图片名，保证唯一
            String photoName = user.getUserId() + ".jpg";
            String photoPath = request.getServletContext().getRealPath("/picture/temp")+"/"+photoName;
            String newPath =  request.getServletContext().getRealPath("/picture")+"/"+photoName;
            if (file!=null&&!file.isEmpty()) {
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(request.getServletContext().getRealPath("/picture")
                                ,  photoName));
                return result;
            }else {
                return result;
            }
        }catch (InformationFormatterFault e){
            logger.warn("wrong formatter.\t");
            return  new RequestResult<User>(StatEnum.INFORMATION_FORMMATTER_FAULT,null);
        }catch (RuntimeException e){
            logger.warn("exist userName.\t");
            return  new RequestResult<User>(StatEnum.REGISTER_ALREADY_EXIST,null);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }


    /**
     * 查看用户信息
     * @param request 请求对象
     * @return dto对象
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<User> showUserInfo(HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            RequestResult<User> result = new RequestResult<User>(StatEnum.INFO_SHOW_SUCCESS,user);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }


    /**
     * 用户登出
     * @param request
     * @return
     */
    @RequestMapping(value = "/signout",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<?> signOut(HttpServletRequest request,HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            return new RequestResult<Object>(StatEnum.USER_SIGN_OUT_SUCCESS);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG);
        }
    }
}
