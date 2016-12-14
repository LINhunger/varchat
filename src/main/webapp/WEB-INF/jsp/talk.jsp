<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>聊天页面</title>
	<%@include file="common/head.jsp"%>
	<%@include file="common/tag.jsp"%>
<script type="text/javascript" src="<%=basePath2%>resources/jquery.js"></script>
    <style type="text/css" href="/styles/main.css"></style>
<style>
textarea {
	height: 300px;
	width: 100%;
	resize: none;
	outline: none;
}

input[type=button] {
	float: right;
	margin: 5px;
	width: 50px;
	height: 35px;
	border: none;
	color: white;
	font-weight: bold;
	outline: none;
}

.clear {
	background: red;
}

.send {
	background: green;
}

.clear:active {
	background: yellow;
}

.send:active {
	background: yellow;
}

.msg {
	width: 100%;
	height: 25px;
	outline: none;
}

#content {
	border: 1px solid gray;
	width: 100%;
	height: 400px;
	overflow-y: scroll;
}

.from {
	background-color: green;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: left;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.to {
	background-color: gray;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: right;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.name {
	color: gray;
	font-size: 12px;
}

.tmsg_text {
	color: white;
	background-color: #8bec63;
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
	display: inline-block;
}

.fmsg_text {
	color: white;
	background-color: #63d9ec;
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
	display: inline-block;
}

.sfmsg_text {
	color: white;
	background-color: rgb(148, 16, 16);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.tmsg {
	clear: both;
	text-align: right;
}

.fmsg {
	clear: both;
	text-align: left;
}
</style>
<script>
		var path = '<%=basePath%>';
		var uid=${sessionScope.user.userId};
		if(uid==-1){
			location.href="<%=basePath2%>";
		}
		var from=uid;
		var fromName='${user.userName}';
		var to=${receiverId};
		
		var websocket;
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://" + path + "ws?userId="+uid);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + path + "ws"+uid);
		} else {
			websocket = new SockJS("http://" + path + "ws/sockjs"+uid);
		}
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
			console.log(event);
			<c:forEach var="msg" items="${msgs}">
					<%--console.log("WebSocket:收到一条消息",'${msg.text}');--%>
					var textCss= ${msg.senderId}==${sessionScope.user.userId}?"tmsg_text":"fmsg_text";//todo
					var msgCss= ${msg.senderId}==${sessionScope.user.userId}?"tmsg":"fmsg";//todo
					var text = '${msg.text}';
			var str = "<div class='"+msgCss+"'><label class='name'>"+"${msg.senderName}" +"&nbsp;"+"<fmt:formatDate value="${msg.date}" pattern="yyyy-MM-dd HH:mm:ss"/>" +"</label><br/><div class='"+textCss+"'>"+text+"</div></div>";
			$("#content").append(str);
			</c:forEach>
			scrollToBottom();
		};
		websocket.onmessage = function(event) {
			var data=JSON.parse(event.data);
			console.log("WebSocket:收到一条消息",data);
			var textCss=data.from==-1?"sfmsg_text":"fmsg_text";//todo
			$("#content").append("<div class='fmsg'><label class='name'>"+data.senderName+"&nbsp;"+data.date+"</label><br/><div class='"+textCss+"'>"+data.text+"</div></div>");
			scrollToBottom();
		};
		websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
			console.log(event);
		};
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭");
			console.log(event);
		}
			function sendMsg(){
				var v=$("#msg").val();
				if(v==""){
					return;
				}else{
					var hichat = new HiChat();
					v=hichat._showEmoji(v);
					var data={};
					data["senderId"]=from;
					data["senderName"]=fromName;
					data["receiverId"]=to;
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#content").append("<div class='tmsg'><label class='name'>我&nbsp;"+new Date().Format("yyyy-MM-dd hh:mm:ss")+"</label><br/><div class='tmsg_text'>"+data.text+"</div></div>");
					scrollToBottom();
					$("#msg").val("");
				}
			}
			
			function scrollToBottom(){
				var div = document.getElementById('content');
				div.scrollTop = div.scrollHeight;
			}
			
			Date.prototype.Format = function (fmt) {
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
			        "S": this.getMilliseconds() //毫秒 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			
			function send(event){
				var code;
				 if(window.event){
					 code = window.event.keyCode; // IE
				 }else{
					 code = e.which; // Firefox
				 }
				if(code==13){ 
					sendMsg();            
				}
			}
			
			function clearAll(){
				$("#content").empty();
			}
		</script>
</head>
<body>
	欢迎：${sessionScope.user.userName }
	<div id="content"></div>
	<input type="text" placeholder="请输入要发送的信息" id="msg" class="msg" onkeydown="send(event)">
	<input type="button" value="发送" class="send" onclick="sendMsg()" >
	<input type="button" value="清空" class="clear" onclick="clearAll()">
	<br/>
	<div id="emojiWrapper">
	</div>
    <div class="controls">
        <div class="items">
            <input id="emoji" type="button" value="表情" title="emoji">
        </div>
        <div id="emojiWrapper" style="display: none;"></div>
	</div>
</body>
<script src="/scripts/hichat.js"></script>
</html>
