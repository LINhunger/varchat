package com.varchat.web;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.varchat.dto.RequestResult;
import com.varchat.model.Message;
import com.varchat.model.User;
import com.varchat.service.MsgService;
import com.varchat.service.UserService;
import com.varchat.websocket.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import com.google.gson.GsonBuilder;
@Controller
@RequestMapping("/msg")
public class MsgController {




	@Autowired
	private UserService userService;
	@Autowired
	private MsgService msgService;
	@Resource
	MyWebSocketHandler handler;
	Map<Long, User> users = new HashMap<Long, User>();
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model){
		//list.jsp+ model= ModelAndView
		return  "indexs";///WEB-INF/jsp/list.jsp
	}
	// 用户登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(User user, HttpServletRequest request) {

		RequestResult<User> result = userService.login(user.getUserName(),user.getPassword());
		//将用户存到session中
		user = result.getData();
		request.getSession().setAttribute("user",user);
		return "friend";
	}

	// 跳转到交谈聊天页面
	@RequestMapping(value = "/{receiverId}/talk", method = RequestMethod.GET)
	public ModelAndView talk(@PathVariable("receiverId") Integer receiverId,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Message> msgs = msgService.selectAllById(user.getUserId(),receiverId);
		ModelAndView mav = new ModelAndView("talk");
		mav.addObject("msgs",msgs);
		mav.addObject("receiverId",receiverId);
		return mav;
	}

	// 跳转到发布广播页面
	@RequestMapping(value = "broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		return new ModelAndView("broadcast");
	}



	// 发布系统广播（群发）
	@ResponseBody
	@RequestMapping(value = "broadcast", method = RequestMethod.POST)
	public void broadcast(String text) throws IOException {
		Message msg = new Message();
		msg.setDate(new Date());
		msg.setSenderId(-1);
		msg.setSenderName("系统广播");
		msg.setReceiverId(0);
		msg.setText(text);
		handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}
}