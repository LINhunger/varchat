package com.varchat.web;

import com.varchat.dao.ApplyDao;
import com.varchat.dto.RequestResult;
import com.varchat.enums.StatEnum;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.varchat.websocket.MyWebSocketHandler.userSocketSessionMap;

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


    @RequestMapping("/send/{receiverId}")
    @ResponseBody
    public RequestResult<?> sendApply(@PathVariable("receiverId") Integer receiverId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Apply apply = new Apply();
        apply.setReceiverId(receiverId);
        apply.setStatus(0);
        apply.setSender((User)session.getAttribute("user"));
        RequestResult result = friendService.insertApply(apply);
        return  result;
    }
    /**
     * 根据组织id查找用户
     * @param request
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public RequestResult<List> searchById(HttpServletRequest request){
        try {
            User user = (User) request.getSession().getAttribute("user");
           String userName = request.getParameter("userName");
            List<User> users = friendService.searchUser(userName);
            for(User u:users) {
                if (u.getUserId()==user.getUserId()) {
                    users.remove(u);break;
                }
            }
            return new RequestResult<List>(StatEnum.SEARCH_USER_SUCCESS, users);
        } catch (Exception e){
            return new RequestResult<List>(StatEnum.DEFAULT_WRONG);
        }
    }
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
    @RequestMapping(value = "/fri",method = RequestMethod.GET)
    public String friend(Model model,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<User> friends = userService.selectFriends(user.getUserId());
        user.setFriends(friends);
        request.setAttribute("user",user);
        return  "friend";
    }

    /**
     * 查看搜索页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/searchs",method = RequestMethod.GET)
    public String search(Model model,HttpServletRequest request){
        return  "search";
    }
}
