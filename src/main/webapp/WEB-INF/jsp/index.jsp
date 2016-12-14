<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录页面</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/tag.jsp"%>
    <style>
        #login {
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
            <h1>登录页面</h1>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <form action="/user/login" method="post">
                <tbody>
                    <h2>${message}</h2>
                    <tr>
                        <th>昵称</th><td><input name="userName" value="" /></td>
                    </tr>
                    <tr>
                        <th>密码</th><td><input type="password" name="password" value="" /></td>
                    </tr>
                    <tr>
                        <td><a class="btn btn-info" href="/user/reg">注册</a> </td>
                        <td><input type="submit" value="登录" class="btn btn-success"id = "login"/></td>
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