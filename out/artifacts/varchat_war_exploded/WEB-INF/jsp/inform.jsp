<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cmn-Hans"><head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>WeUI</title>
    <%@include file="common/tag.jsp"%>
    <link rel="stylesheet" href="../css/weui.css">
    <link rel="stylesheet" href="../css/my.css">
</head>
<style>
</style>
<body ontouchstart="">
<div class="container" id="container">
    <h3 class="my-title" style="margin-bottom: -20px;">${user.userName}</h3>
    <div class="page__bd">
        <div class="weui-cells">
            <c:choose>
            <c:when test="${empty applys}">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <p style="text-align: center; padding: 40px;">尚无通知</p>
                    </div>
                </div>
            </c:when>
                <c:otherwise>
                    <c:forEach var="apply" items="${applys}">
                        <div class="weui-cell resultItem">
                            <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
                                <img src="../images/${apply.sender.picture}" style="width: 50px;display: block">
                            </div>
                            <div class="weui-cell__bd">
                                <p>${apply.sender.userName}</p>
                                <p style="font-size: 13px;color: #888888;">请求添加你为好友</p>
                            </div>
                            <div class="button-sp-area">
                                <a class="weui-btn weui-btn_mini weui-btn_primary" href="/friend/${apply.receiverId}/${apply.sender.userId}/${apply.applyId}/1/execution">同意</a>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="page__td">
            <div class="weui-tabbar">
                <a href="/friend/fri" class="weui-tabbar__item weui-bar__item_on">
                    <span style="display: inline-block;position: relative;">
                        <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    </span>
                    <p class="weui-tabbar__label">好友</p>
                </a>
                <a href="/friend/list" class="weui-tabbar__item">
                    <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">通知</p>
                </a>
                <a href="/friend/searchs" class="weui-tabbar__item">
                    <span style="display: inline-block;position: relative;">
                        <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    </span>
                    <p class="weui-tabbar__label">发现</p>
                </a>
                <a href="/user/signout;" class="weui-tabbar__item">
                    <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">退出</p>
                </a>
            </div>
        </div>
    </div>
</div>
<script src="../js/zepto.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="../js/weui.min.js"></script>
</body>
</html>