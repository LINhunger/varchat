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
}