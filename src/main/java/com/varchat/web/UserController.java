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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
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

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return  "index";
    }
    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public String reg(){
        return  "register";
    }
    // 用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(Model model, User user, HttpServletRequest request) {
        try {
            user.setUserName(new String(user.getUserName().getBytes("ISO-8859-1"),"UTF-8"));
            RequestResult<User> result = userService.login(user.getUserName(),user.getPassword());
            //将用户存到session中
            user = result.getData();
            request.getSession().setAttribute("user",user);
            return "friend";
        }catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            return "index";
        }
    }


    /**
     * 注册
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest request, User user) {

        //注册用户
        try {
            user.setUserName(new String(user.getUserName().getBytes("ISO-8859-1"),"UTF-8"));
            RequestResult<User> result = userService.register(user);
            request.getSession().setAttribute("user",result.getData());
            return new ModelAndView("friend");
        }catch (RegisterFormatterFaultException e) {
            logger.warn("wrong formatter.\t");
            return new ModelAndView("register").addObject("message","注册格式错误,密码只能1-15位，用户名只能由数字字母汉字组成");
        }catch (DataIntegrityViolationException e) {//主键重复异常
            logger.warn("user is already exist.\t");
            return new ModelAndView("register").addObject("message","已存在的用户");
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return new ModelAndView("register").addObject("message","其他错误");
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
    public String signOut(HttpServletRequest request,HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            userService.signout(user.getUserId());
            request.getSession().invalidate();
            return "index";
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return "index";
        }
    }
}
