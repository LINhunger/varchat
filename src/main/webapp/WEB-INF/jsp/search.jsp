<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cmn-Hans"><head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>WeUI</title>
<link rel="stylesheet" href="../css/weui.css">
<link rel="stylesheet" href="../css/my.css">
</head>
<body ontouchstart="">
<div class="container" id="container">
    <h3 class="my-title">${user.userName}</h3>
    <div class="page__bd searchbar js_show">
        <div class="weui-search-bar" id="searchBar" style="height: 45px;">
            <form class="weui-search-bar__form">
                <div class="weui-search-bar__box">
                    <i class="weui-icon-search"></i>
                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
                </div>
                <label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
                    <i class="weui-icon-search"></i>
                    <span>搜索</span>
                </label>
            </form>
            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
        </div>
        <div class="weui-cells searchbar-result" id="searchResult" style="display: none; transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
        </div>
    </div>


    <div class="page__td">
        <div class="weui-tabbar">
            <a href="/friend/fri" class="weui-tabbar__item">
                    <span style="display: inline-block;position: relative;">
                        <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    </span>
                <p class="weui-tabbar__label">好友</p>
            </a>
            <a href="/friend/list" class="weui-tabbar__item">
                <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <p class="weui-tabbar__label">通知</p>
            </a>
            <a href="/friend/searchs" class="weui-tabbar__item weui-bar__item_on">
                    <span style="display: inline-block;position: relative;">
                        <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    </span>
                <p class="weui-tabbar__label">发现</p>
            </a>
            <a href="/user/signout" class="weui-tabbar__item">
                <img src="../images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <p class="weui-tabbar__label">退出</p>
            </a>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="../js/jquery-3.1.0.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="../js/weui.min.js"></script>
<script type="text/javascript">
    $(function(){
        var $searchBar = $('#searchBar'),
                $searchResult = $('#searchResult'),
                $searchText = $('#searchText'),
                $searchInput = $('#searchInput'),
                $searchClear = $('#searchClear'),
                $searchCancel = $('#searchCancel');

        /**
         * [addSearchResults 插入搜索结果]
         * @param {[type]} infos [description]
         */
        function addSearchResults(infos){
            if(infos.length<=0){
                var result = '<div class="weui-cell">\
                                    <div class="weui-cell__bd">\
                                        <p style="text-align: center; padding: 40px;">该用户不存在</p>\
                                    </div>\
                                </div>';
                $searchResult[0].innerHTML = result;
            }else{
                var result = '';
                for(var i=0; i<infos.length; i++){
                    var info = infos[i];
                    var str = '<div class="weui-cell resultItem" href="javascript:;">\
                            <div class="weui-cell__hd">\
                                <img src="/images/'+info.picture+'" alt="头像" style="width:40px;margin-right:5px;display:block">\
                            </div>\
                            <div class="weui-cell__bd">\
                                <p>'+info.userName+'</p>\
                                <p style="font-size: 13px;color: #888888;"></p>\
                            </div>\
                            <div class="button-sp-area">\
                                <button class="weui-btn weui-btn_mini weui-btn_primary addQuest" userId="'+info.userId+'">添加</button>\
                            </div>\
                        </div>';
                    result += str;
                }
                $searchResult[0].innerHTML = result;

                //为按钮添加事件
                $(".addQuest").click(function(){
                    var receiverId = $(this).attr('userId');
                    $.ajax({
                        type: "POST",
                        url: "/friend/send/"+receiverId,
                        contentType: "application/json; charset=utf-8",
                        data: {receiverId: receiverId},
                        dataType: "json",
                        success: function(data){
                            alert(data.stateInfo);
                        }
                    })
                })
            }
        }

        function hideSearchResult(){
            $searchResult.hide();
            $searchResult[0].innerHTML='';  //改
            $searchInput.val('');
        }

        function cancelSearch(){
            hideSearchResult();
            $searchBar.removeClass('weui-search-bar_focusing');
            $searchText.show();
        }

        $searchText.on('click', function(){
            $searchBar.addClass('weui-search-bar_focusing');
            $searchInput.focus();
        });

        $searchInput.on('blur', function () {
            if(!this.value.length) cancelSearch();
        }).on('input', function(){
            if(this.value.length) {
                var url = "/friend/search?userName="+this.value;
                $.post(url,function(data){
                    if(data.state == 201){
                        addSearchResults(data.data);
                    }else{
                        alert(data.stateInfo);
                    }
                },"json");
                $searchResult.show();
            } else {
                $searchResult.hide();
            }
        });

        $searchClear.on('click', function(){
            hideSearchResult();
            $searchInput.focus();
        });
        $searchCancel.on('click', function(){
            cancelSearch();
            $searchInput.blur();
        });
    });
</script>
</html>
