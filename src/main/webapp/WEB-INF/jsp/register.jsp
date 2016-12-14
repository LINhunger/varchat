<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的提醒</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/tag.jsp"%>
    <style>
        #register {
            padding: 6px 57px;
        }
    </style>
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
            <h1>注册页面</h1>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <form action="/user/register" method="post">
                    <tbody>
                    <h2>${message}</h2>
                    <tr>
                        <th>昵称</th><td><input name="userName" value="" /></td>
                    </tr>
                    <tr>
                        <th>密码</th><td><input type="password" name="password" value="" /></td>
                    </tr>
                    <tr>
                        <td><a class="btn btn-success" href="/user/index">登录</a> </td>
                        <td><input type="submit" value="注册" class="btn btn-info"id = "register"/></td>
                    </tr>
                    </tbody>
                </form>
            </table>
        </div>
    </div>
</div>






</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
