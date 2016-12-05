package com.varchat.websocket;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.varchat.model.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
/**
 * Socket建立连接（握手）和断开
 * 
 * @author LINhunger
 */
public class HandShake implements HandshakeInterceptor {
	public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {
		System.out.println("Websocket:用户[ID:"+ ((ServletServerHttpRequest) request).getServletRequest()
		.getSession(false).getAttribute("user") + "]已经建立连接");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			// 标记用户
			User user = (User) session.getAttribute("user");
			if (user != null) {
				attributes.put("uid", user.getUserId());
			} else {
				return false;
			}
		}
		return true;
	}
	public void afterHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("握手成功");
	}
}