package com.varchat.web;

import com.varchat.dao.ApplyDao;
import com.varchat.model.Apply;
import com.varchat.model.User;
import com.varchat.service.FriendService;
import com.varchat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hunger on 2016/12/4.
 */
@Controller
@RequestMapping("/friend")
public class FriendController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    /**
     * 查看申请列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String applyList(Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Apply> applys = friendService.selectAllByUserId(user.getUserId());
        model.addAttribute("applys",applys);
        logger.info("list  invoke:");
        return  "inform";
    }

    /**
     * 处理申请
     * @return
     */
    @RequestMapping(value = "/{receiverId}/{userId}/{applyId}/{status}/execution",method = RequestMethod.GET)
    public String applyExecution(@PathVariable("receiverId") Integer receiverId,
                                 @PathVariable("userId") Integer userId,
                                 @PathVariable("applyId") Integer applyId,
                                 @PathVariable("status") Integer status
    ){
        Apply apply = new Apply();
        apply.setReceiverId(receiverId);
        apply.setApplyId(applyId);
        apply.setSender(friendService.selectUser(userId));
        apply.setStatus(status);
        //处理申请信息
        friendService.updateApply(apply);
        logger.info("execution  invoke:");
        return  "redirect:/friend/list";
    }
    /**
     * 查看群成员列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/menbers",method = RequestMethod.GET)
    public String menberList(Model model,HttpServletRequest request){
        return  "friend";
    }
}
