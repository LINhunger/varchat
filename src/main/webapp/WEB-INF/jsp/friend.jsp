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
                <c:when test="${empty sessionScope.user.friends}">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <p style="text-align: center; padding: 40px;">还没有好友</p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="friend" items="${sessionScope.user.friends}">
                        <div class="weui-cell weui-cell_access" onclick="hange(${friend.userId})">
                            <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
                                <img src="../images/${friend.picture}" style="width: 50px;display: block">
                            </div>
                            <div class="weui-cell__bd">
                                <p>${friend.userName}</p>
                                <c:choose>
                                    <c:when test="${friend.isOnline==0}">
                                        <p style="font-size: 13px;color: #888888;">离线</p>
                                    </c:when>
                                    <c:when test="${friend.isOnline==1}">
                                        <p style="font-size: 13px;color: #888888;">在线</p>
                                    </c:when>
                                </c:choose>
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
<script type="text/javascript" src="../js/jweixin-1.0.0.js"></script>
<script src="../js/weui.min.js"></script>
<script type="text/javascript">
    function hange(friendId){
        window.location.href="/msg/"+friendId+"/talk";
    }
</script>
</body>
</html>