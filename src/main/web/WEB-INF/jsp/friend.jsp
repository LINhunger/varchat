<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>好友列表</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/tag.jsp"%>
</head>
<style>
    #hunger td,  #hunger th{
        text-align: center;
    }
</style>
<body>
<%--页面显示部分--%>
<div class="container" id="hunger">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h1>好友列表</h1>
        </div>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation"class="active"><a href="../${sessionScope.organId}/list">申请列表</a></li>
            <li role="presentation"><a href="../${sessionScope.organId}/menbers">成员列表</a></li>
            <li role="presentation"><a href="../${sessionScope.organId}/homework">作业列表</a></li>
            <li role="presentation"><a href="/anywork/src/html/createHomework.html">发布试卷</a></li>
        </ul>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>头像</th>
                    <th>名称</th>
                    <th>聊天</th>
                </tr>
                </thead>
                <tbody>

                <c:if test="${empty sessionScope.user.friends}">
                    <h2>还没有好友！</h2>
                </c:if>
                <c:forEach var="friend" items="${sessionScope.user.friends}">
                        <tr onclick="hange(${friend.userId})">
                            <td>${friend.picture}</td>
                            <td>${friend.userName}</td>
                            <td>   ${friend.userId}</td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>






</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function hange(friendId){
        window.location.href="/msg/"+friendId+"/talk";
    }
    $(function(){

    })
</script>
</html>
