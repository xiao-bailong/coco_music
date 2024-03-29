    // 监听浏览器关闭
    window.onbeforeunload = function() {
        if (lists){
            var music={
                musicid:lists[musicIndex].id,
                musici:musicIndex,
                musiclists:lists,
                currenttime:audio.currentTime,
                playmode:$('.sound>span:nth-child(2) i').attr('class'),
            };
            localStorage.setItem('music',JSON.stringify(music));
        }
    };

    var music=JSON.parse(localStorage.getItem('music'));
    if (music) {
        lists=music.musiclists;
        musicIndex=music.musici;
        var str='';
        if(!lists[0].al){
            for (let i=0;i<lists.length;i++){
                str+='<li data-index='+i+'>' +
                    '<span>'+lists[i].name+'</span>\n' +
                    '<span>'+lists[i].artists[0].name+'</span>\n' +
                    '</li>'
            }
        } else {
            for (let i=0;i<lists.length;i++){
                str+='<li data-index='+i+'>' +
                    '<span>'+lists[i].name+'</span>\n' +
                    '<span>'+lists[i].ar[0].name+'</span>\n' +
                    '</li>'
            }
        }
        $('.list-pop .list>ul').html(str);
        $('.list-pop .list>ul li:eq('+musicIndex+') span').css('color','rgb(198, 46, 46)');
        $('.sound span:nth-child(4) span').text(lists.length);
        // musicInit();
        audio.currentTime=music.currenttime;
        $('.sound>span:nth-child(2) i').attr('class',music.playmode);
        music=null;
    }





    //音量弹窗隐藏和显示
    $('.sound>span:eq(2)').click(function(){
        $('.sound-pop').fadeToggle(200);
    });

    //点击歌曲图片，弹出歌词页面
    $('.headimg').click(function(){
        $('.music-pop').stop().fadeToggle(200);
        if($('.headimg>div>span:eq(0) i').attr('class')=='iconfont icon-zuoxia01'){
            $('.headimg>div>span:eq(0) i').attr('class','iconfont icon-youshang01');
            $('.headimg>div>span:eq(1) i').attr('class','iconfont icon-zuoxia01');
        }else{
            $('.headimg>div>span:eq(0) i').attr('class','iconfont icon-zuoxia01');
            $('.headimg>div>span:eq(1) i').attr('class','iconfont icon-youshang01');
        }
    });

    //点击按钮，关闭歌词页面
    $('.music-pop .lyr>.right>p>span:nth-child(3)').click(function(){
        $('.music-pop').stop().fadeToggle(200);
        $('.headimg>div>span:eq(0) i').attr('class','iconfont icon-zuoxia01');
        $('.headimg>div>span:eq(1) i').attr('class','iconfont icon-youshang01');
    });

    //鼠标移入歌曲图片，显示箭头,移除隐藏
    $('.headimg').mouseover(function(){
        if (browserRedirect()=='pc'){
            $('.headimg>div').show();
        }
    });
    $('.headimg').mouseout(function(){
        if (browserRedirect()=='pc') {
            $('.headimg>div').hide();
        }
    });

    //为每个nav下的li绑定点击事件
    $('.loading').show();
    $.ajax({
        url:'find.html',
        type:'get',
        success:function (res) {
            $('section').html(res);
            getBanner();
            getDayDate();
        }
    });
    // window.history.pushState({index:2}, null, 'findMusic.html');

    $('.nav-icon li').click(function(){
        if($(this).attr('data-index')!=0&&$(this).attr('data-index')!=5){
            $('.nav-icon li').removeClass("active");
            $(this).addClass("active");
            var type=$(this).attr('type');
            if ($(this).attr('data-index')==1) {
                $('.loading').show();
                $('.loading').fadeOut();
                $.ajax({
                    url:'../coco_music_war_exploded/search.html',
                    type:'get',
                    success:function (html) {
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(html);
                        $.ajax({
                            url:'SearchServlet?method=GetHistory',
                            type:'get',
                            dataType:'json',
                            success:function (data) {
                                console.log(data);
                                var str='';
                                for (let i=0;i<data.values.length;i++){
                                    str+='<li data-id='+data.values[i].id+'>' +
                                        '<span>'+data.values[i].history+'</span>' +
                                        '<i class="iconfont icon-x"></i>' +
                                        '</li>';
                                }
                                $('.search .main .right ul').html(str);
                            }
                        });
                        /*$('section').on('click','.search .input span',function () {
                            var $key=$('.search .input input').val();
                            console.log($key);
                            var datas={
                                key:$key
                            };
                            getSong(datas);
                        });*/
                        hist(type);
                    }
                });
            }else if ($(this).attr('data-index')==2){
                $('.loading').show();
                $.ajax({
                    url:'../coco_music_war_exploded/find.html',
                    type:'get',
                    success:function (res) {
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(res);
                        getBanner();
                        getDayDate();
                        hist(type);
                    }
                });
            }else if ($(this).attr('data-index')==8){
                var type=$(this).attr('type');
                if(islogin==false){
                    alert("请先登录");
                    $('.loading').show();
                }
                $.ajax({
                    url:'../coco_music_war_exploded/cd.html',
                    type:'get',
                    success:function (html) {
                        // var id=$('.nav-icon .local li:nth-child(5)').attr('data-id');
                        // if (id){
                        //     history.replaceState({text:$('section').html()}, '');
                        //     $('section').html(html);
                        //     $.ajax({
                        //         url:'/playlist/detail?id='+id,
                        //         type:'get',
                        //         success:function (data) {
                        //             haveLists=data.playlist.tracks;
                        //             $('.author-cd .top>p').text('歌单');
                        //             $('.author-cd .top>div img').attr('src',data.playlist.coverImgUrl);
                        //             $('.author-cd .top>div p:nth-child(2)').text('我喜欢的音乐');
                        //             $('.author-cd .top>div p:nth-child(3)').text(fmtDate(data.playlist.createTime)+' 创建');
                        //             $('.author-cd .top>div p:nth-child(4)').text('');
                        //             var str='';
                        //             for (let i=0;i<data.playlist.tracks.length;i++){
                        //                 str+='<li>' +
                        //                     '<span>'+num(i+1)+'</span>' +
                        //                     '<span>'+data.playlist.tracks[i].name+'</span>' +
                        //                     '<span>'+data.playlist.tracks[i].ar[0].name+'</span>' +
                        //                     '<span>热度：'+data.playlist.tracks[i].pop+'</span>' +
                        //                     '</li>'
                        //             }
                        //             $('.author-cd .bottom>ul').html(str);
                        //             $('.loading').fadeOut();
                        //             hist(type);
                        //         }
                        //     });
                        // }else {
                        //     alert('请先登录');
                        // }
                        //syx
                        var datas={
                            user_id:currentUser
                        };
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(html);
                        $.ajax({
                            url:'SonglikeServelet?method=GetSonglikeListData',
                            type:'post',
                            dataType:'json',
                            data:datas,
                            success:function (data) {
                                haveLists=data
                                console.log(data);
                                // haveLists=data.playlist.tracks;
                                $('.author-cd .top>p').text('歌单');
                                $('.author-cd .top>div img').attr('src',data.values[0].picurl);
                                $('.author-cd .top>div p:nth-child(2)').text('我喜欢的音乐');
                                // $('.author-cd .top>div p:nth-child(3)').text(fmtDate(data.playlist.createTime)+' 创建');
                                $('.author-cd .top>div p:nth-child(4)').text('');
                                var str='';
                                for (let i=0;i<data.values.length;i++){
                                    str+='<li>' +
                                        '<span>'+num(i+1)+'</span>' +
                                        '<span>'+data.values[i].song_name+'</span>' +
                                        '<span>'+data.values[i].album_name+'</span>' +
                                        '<span>热度：'+data.values[i].pop+'</span>' +
                                        '</li>'
                                }
                                $('.author-cd .bottom>ul').html(str);
                                $('.loading').fadeOut();
                                hist(type);
                            }
                        });
                        //new
                        // history.replaceState({text:$('section').html()}, '');
                        //     $('section').html(html);
                        //     $.ajax({
                        //         url:'/playlist/detail?id='+id,
                        //         type:'get',
                        //         success:function (data) {
                        //             haveLists=data.playlist.tracks;
                        //             $('.author-cd .top>p').text('歌单');
                        //             $('.author-cd .top>div img').attr('src',data.playlist.coverImgUrl);
                        //             $('.author-cd .top>div p:nth-child(2)').text('我喜欢的音乐');
                        //             $('.author-cd .top>div p:nth-child(3)').text(fmtDate(data.playlist.createTime)+' 创建');
                        //             $('.author-cd .top>div p:nth-child(4)').text('');
                        //             var str='';
                        //             for (let i=0;i<data.playlist.tracks.length;i++){
                        //                 str+='<li>' +
                        //                     '<span>'+num(i+1)+'</span>' +
                        //                     '<span>'+data.playlist.tracks[i].name+'</span>' +
                        //                     '<span>'+data.playlist.tracks[i].ar[0].name+'</span>' +
                        //                     '<span>热度：'+data.playlist.tracks[i].pop+'</span>' +
                        //                     '</li>'
                        //             }
                        //             $('.author-cd .bottom>ul').html(str);
                        //             $('.loading').fadeOut();
                        //             hist(type);
                        //         }
                        //     });
                    }
                });
            }else if ($(this).attr('data-index')==3){
                $('.loading').show();
                $.ajax({
                   url:'../coco_music_war_exploded/mvSecond.html',
                    type:'get',
                    success:function (html) {
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(html);
                        mvOffset=0;
                        getmvsecond('http://localhost:3000/top/mv?limit=20').then(function (data) {
                            $('.mv-second .paihangbang').html(data);
                            $('.loading').fadeOut();
                            hist('mvSecond');
                        });
                        mvSecondScrollBottomTest();
                    }
                });
            }

        }else{
            if($(this).width()!=50){
                $('section').stop().animate({'margin-left':'51px'},100);
                $('.loading').stop().animate({'left':'51px'},100);
                $('.ajaxload').stop().animate({'left':'51px'},100);
                $('.nav-icon li span').hide();
                $('.nav-icon li:eq(5)').css('visibility','visible');
                $('.nav-icon .local').hide();
                $('.login>span>span').hide();
                $('.nav-icon').stop().animate({'width':'50px'},100);
            }else{
                if(browserRedirect()!='phone'){
                    $('section').stop().animate({'margin-left':'201px'},300);
                }

                $('.loading').stop().animate({'left':'201px'},300);
                $('.ajaxload').stop().animate({'left':'201px'},300);
                $('.nav-icon').stop().animate({'width':'200px'},300,function(){
                    $('.nav-icon li span').show();
                    $('.nav-icon li:eq(5)').css('visibility','hidden');
                    $('.nav-icon .local').show();
                    $('.login>span>span').show();
                });
            }
        }
    });

    //按下空格。暂停歌曲
    $(document).keydown(function (e) {
        if (e.which == "32"&&!$('section .search .input input').is(':focus')) {
            if(audio.paused){
                audio.play();
            }else {
                audio.pause();
            }

        }
    });

    //搜索歌曲
    // $('section').on('click','.search .input span',function () {
    //     var text=$('.search .input input').val();
    //     musicText=text;
    //     search(text);
    // });
    $('section').on('click','.search .input span',function () {
        var dataIndex=$('.search .searchBody>ul .active').attr('data-index');
        var $key=$('.search .input input').val();
        var datas={
            key:$key
        };
        console.log(dataIndex);
        if(dataIndex==4){
            getSongList(datas);
        }else{
            getSong(datas);
        }
    });
    // $('section').keydown('.search .input span',function (e) {
    //     if (e.which == "13") {
    //         var text=$('.search .input input').val();
    //         musicText=text;
    //         search(text);
    //     }
    // });
    //热门搜索
    $('section').on('click','.search .main .left span',function () {
        var text=$(this).text();
        musicText=text;
        var datas={
            key:text
        };
        $('.search .input input').attr('value',musicText);
        getSong(datas);
        $('.search .input input').attr('value',musicText);
    });
    //搜索历史
    $('section').on('click','.search .main .right li span:nth-child(1)',function () {
        var text=$(this).text();
        musicText=text;
        var datas={
            key:text
        };
        $('.search .input input').attr('value',musicText);
        getSong(datas);
        $('.search .input input').attr('value',musicText);
    });
    //删除搜索历史
    $('section').on('click','.search .main p i',function () {
        // var $id=$('.search .main .right ul li').attr('data-id');
        var $id=$(this).parent().attr('data-id');
        /*$('.search .main .right ul').animate({'marginLeft':'110%'},200,function () {
            $('.search .main .right ul li').remove();
            // localStorage.removeItem('songs');
        });*/
        var datas={
            id:$id
        };
        console.log(datas);
        $.ajax({
            url:'SearchServlet?method=DeleteHistory',
            type:'post',
            dataType:'json',
            data:datas,
            success:function(data){
                if("success" == data.type){
                    alert(data.msg);
                }
                $('.search .main .right ul').animate({'marginLeft':'110%'},200,function () {
                    $('.search .main .right ul li').remove();
                });
            }
        });
    });

    $('section').on('click','.search .main .right li i',function () {
        var li=$(this).parent();
        /*li.animate({'marginLeft':'110%'},200,function () {
            li.remove();
            var text=li.children(':first').text();
            var songs=JSON.parse(localStorage.getItem('songs'));
            var i=songs.indexOf(text);
            songs.splice(i, 1);
            localStorage.setItem('songs',JSON.stringify(songs));
        });*/
        var $id=$(this).parent().attr('data-id');
        var datas={
            id:$id
        };
        console.log(datas);
        $.ajax({
            url:'SearchServlet?method=DeleteHistory',
            type:'post',
            dataType:'json',
            data:datas,
            success:function(data){
                /*if("success" == data.type){
                    alert(data.msg);
                }*/
                alert(data.msg);
                li.animate({'marginLeft':'110%'},200,function () {
                    li.remove();
                    var text=li.children(':first').text();
                    var songs=JSON.parse(localStorage.getItem('songs'));
                    var i=songs.indexOf(text);
                    songs.splice(i, 1);
                    localStorage.setItem('songs',JSON.stringify(songs));
                });
            }
        });
    });

    //搜索歌曲列表点击
    $('section').on('click','.search .searchBody .song li',function () {
        lists=haveLists;
        musicIndex=$(this).attr('data-index');
        $('.search .searchBody .song li span').css('color','');
        $('.search .searchBody .song li:eq('+musicIndex+') span').css('color','#C62E2E');
        musicInit();
        audio.play();
    });

    //切换搜索类型
    $('section').on('click','.search .searchBody>ul li',function () {
        $(this).addClass('active').siblings().removeClass('active');
        var dataIndex=$(this).attr('data-index');
        $('.search .searchBody>div').hide();
        $('.search .searchBody>div:eq('+dataIndex+')').show();
        var $key=$('.search .input input').val();
        var datas={
            key:$key
        };
        if (dataIndex==0){
            // getSong(musicText);
            getSong(datas);
        }else if (dataIndex==1) {
            // getAuthor(musicText);
        }else if (dataIndex==2) {
            // getCd(musicText);
        }else if (dataIndex==3) {
            // getMv(musicText);
        } else if (dataIndex==4) {
            // getSongList(musicText);
            getSongList(datas);
        }
    });

    //歌手页面点击
    $('section').on('click','.search .searchBody .author>ul li',function () {
        var $this=$(this);
        authorMvOffset=0;
        $('.loading').show();
        $.ajax({
            url:'author.html',
            type:'get',
            success:function (res) {
                history.replaceState({text:$('section').html()}, '');
                $('section').html(res);
                getAuthordetails($this.attr('data-authorId'));
                authorScrollBottomTest();
            }
        });
    });


    //歌手页面播放单曲
    $('section').on('click','.search-author .authorHost>ul li',function () {
        lists=haveLists;
        musicIndex=$(this).index();
        var str='';
        for (let i=0;i<lists.length;i++){
            str+='<li data-index='+i+'>' +
                '<span>'+lists[i].name+'</span>\n' +
                '<span>'+lists[i].ar[0].name+'</span>\n' +
                '</li>'
        }
        $('.list-pop .list>ul').html(str);
        $('.sound span:nth-child(4) span').text(lists.length);
        // musicInit();
        audio.play();
    });

    //歌手页面切换
    $('section').on('click','.search-author .top>ul li',function () {
        var i=$(this).attr('data-index');
        $(this).attr('class','active').siblings().attr('class','');
        $('.search-author .main>div:eq('+i+')').show().siblings().hide();
        if (i==1){
            getAuthorCd();
        }else if (i==2){
            getAuthorMv();
        }else if (i==3){
            getAuthorDeta();
        }
    });




    // 点击mv播放mv
    $('section').on('click','.searchBody .mv>div',function () {
        var id=$(this).attr('data-id');
        getMvUrl(id);
    });
    $('section').on('click','.search-author .authorMv>div',function () {
        var id=$(this).attr('data-id');
        getMvUrl(id);
    });
    $('section').on('click','.find .main .recommend .recommend-mv>div',function () {
        var id=$(this).attr('data-id');
        getMvUrl(id);
    });

    //点击专辑进入专辑页面
    $('section').on('click','.searchBody .cd>ul li',function () {
        var id=$(this).attr('data-id');
        getCdDeta(id);
    });
    $('section').on('click','.search-author .authorCd>div',function () {
        var id=$(this).attr('data-id');
        getCdDeta(id);
    });

    //点击专辑列表，播放歌曲
    // $('section').on('click','.author-cd .bottom>ul li',function () {
    //     musicIndex=$(this).index();
    //     lists=haveLists;
    //     var str='';
    //     for (let i=0;i<lists.length;i++){
    //         str+='<li data-index='+i+'>' +
    //             '<span>'+lists[i].name+'</span>\n' +
    //             '<span>'+lists[i].ar[0].name+'</span>\n' +
    //             '</li>'
    //     }
    //     $('.list-pop .list>ul').html(str);
    //     $('.sound span:nth-child(4) span').text(lists.length);
    //     musicInit();
    //     audio.play();
    // }
    //syx
    $('section').on('click','.author-cd .bottom>ul li',function () {
            musicIndex=$(this).index();
            lists=haveLists;
            var str='';
            for (let i=0;i<lists.values.length;i++){
                str+='<li data-index='+i+'>' +
                    '<span>'+lists.values[i].song_id+'</span>\n' +
                    '<span>'+lists.values[i].song_id+'</span>\n' +
                    '</li>'
            }
            $('.list-pop .list>ul').html(str);
            $('.sound span:nth-child(4) span').text(lists.length);
            musicInit();
            audio.play();
        }
    );

    //登录input输入事件
    // $('.login-pop>div input').on('input',function () {
    //     var userName=$('.login-pop .user input').val();
    //     var password=$('.login-pop .pass input').val();
    //     if (userName!=''&&password!=''){
    //         $('.login-pop button').attr('class','selected');
    //         $('.login-pop button').removeAttr('disabled');
    //     }else {
    //         $('.login-pop button').attr('class','').attr('disabled','');
    //     }
    // });
	
	//注册input输入事件
	// $('.register-pop>div input').on('input',function () {
	//     var userName=$('.register-pop .user input').val();
	//     var password=$('.register-pop .pass input').val();
	//     if (userName!=''&&password!=''){
	//         $('.register-pop button').attr('class','selected');
	//         $('.register-pop button').removeAttr('disabled');
	//     }else {
	//         $('.register-pop button').attr('class','').attr('disabled','');
	//     }
	// });

    /*$('.set-pop>#quit').click(function () {
        $('.nav .login>span:nth-child(1) img').attr('src','images/user.png');
        $('.nav .login>span:nth-child(1) span').text("未登录");
        $('.set-pop').slideUp();//关不掉
    });*/

    /*$('.set-pop>#modify').click(function () {
        // $('.infor-pop').stop().slideToggle();
        $('.infor-pop').stop().animate({width: 'toggle'});
    });
    $('.infor-pop .close').click(function () {
        $('.infor-pop').animate({width: 'toggle'});
    });*/

    // 测试
    /*$("#slidewidth button").click(function(){
        $(this).next().animate({width: 'toggle'});
    });
    $("#slideleft button").click(function(){
        var $lefty = $(this).next();
        $lefty.animate({
            left:parseInt($lefty.css('left'),10)==0 ? -$lefty.outerWidth() : 0
        });
    });
    $("#slidemarginleft button").click(function(){
        var $marginlefty = $(this).next();
        $marginlefty.animate({
            marginLeft:parseInt($marginlefty.css('marginLeft'),10)==0 ? $marginlefty.outerWidth() : 0
        });
    });*/

    //点击未登录，弹出登录框(已登录则弹设置窗）
    $('.nav .user .login>span:nth-child(1)').click(function () {
        if($('.nav .login>span:nth-child(1) span').text()=='未登录'){
            $('.login-pop').stop().slideToggle();
        }
        /*else{
            $('.set-pop').stop().slideToggle();
        }*/
    });

    //关闭个人设置框
    /*$('.set-pop .close').click(function () {
        $('.set-pop').slideUp();
    });*/
    //关闭登录框
    $('.login-pop .close').click(function () {
        $('.login-pop').slideUp();
    });
	//点击注册，弹出注册框
    $('.login-pop>#button2').click(function () {
        $('.register-pop').stop().slideToggle();
    });
	//关闭注册框
	$('.register-pop .close').click(function () {
	    $('.register-pop').slideUp();
	});
    //点击注册按钮(修改版)
    $('.register-pop>button').click(function () {
        if ($(this).text()=='注册'){
            var $name=$('.register-pop .username input').val();
            var $user_id=$('.register-pop .user input').val();
            var $password=$('.register-pop .pass input').val();
            var $password2=$('.register-pop .pass2 input').val();
            if($name=='' ){
                // alert("用户名不能为空");
                $('.register-pop .prompt>span').text('用户名不能为空');
                $('.register-pop .prompt').show();
                return false;
            }
            else if($user_id==''){
                // alert("账号不能为空");
                $('.register-pop .prompt>span').text('账号不能为空');
                $('.register-pop .prompt').show();
                return false;
            }
            else if($password==''){
                // alert("密码不能为空");
                $('.register-pop .prompt>span').text('密码不能为空');
                $('.register-pop .prompt').show();
                return false;
            }
            else if($password!=$password2){
                // alert("两次密码不一致");
                $('.register-pop .prompt>span').text('两次密码不一致');
                $('.register-pop .prompt').show();
                return false;
            } else{
                var datas={
                    name:$name,
                    user_id:$user_id,
                    password:$password
                };
                $.ajax({
                    url:'LoginServlet?method=registeAct',
                    type:'post',
                    dataType:'json',
                    data:datas,
                    success:function(data){
                        if("error" == data.type){
                            // alert(data.msg);
                            $('.register-pop .prompt>span').text(data.msg);
                            $('.register-pop .prompt').show();
                        }else if("success" == data.type){
                            alert(data.msg);
                            $('.register-pop').slideUp();/*关闭注册框*/
                        }
                    },
                    error:function(){
                        alert('false');
                    }
                });
            }
        }else {
            // $('.nav .login>span:nth-child(1) img').attr('src','./images/def.jpg');
            // $('.nav .login>span:nth-child(2) span').text('注册');
            // $('.register-pop .fill').show();
            // // $('.register-pop .user-img').hide();
            // $('.register-pop>button').text('注册');
            // $('.register-pop>button').attr('disabled','');
        }
    });

    //点击登录按钮（修改版）
    $('.login-pop>#button1').click(function () {
        if ($(this).text()=='登录'){
            var $userName=$('.login-pop .user input').val();
            var $password=$('.login-pop .pass input').val();
            if($userName=='' ){
                // alert("账号不能为空");
                $('.login-pop .prompt>span').text('账号不能为空');
                $('.login-pop .prompt').show();
                return false;
            }
            else if($password==''){
                // alert("密码不能为空");
                $('.login-pop .prompt>span').text('密码不能为空');
                $('.login-pop .prompt').show();
                return false;
            }else{
                var datas={
                    username:$userName,
                    pass:$password
                };
                // var datas = $(".login-pop .fill").serialize();
                $.ajax({
                    url:'LoginServlet?method=loginAct',
                    type:'post',
                    dataType:'json',
                    data:datas,
                    success:function(data){
                        if("error" == data.type){
                            // alert(data.msg);
                            $('.login-pop .prompt>span').text(data.msg);
                            $('.login-pop .prompt').show();
                        }else if("success" == data.type){
                            console.log(data);
                            /*if(data.user.sex==undefined) {
                                console.log(data.user.sex);
                                console.log('未定义');
                            }
                            console.log(data.user.birthday);*/
                            alert(data.msg);
                            islogin=true;
                            $('.nav .login>span:nth-child(1) img').attr('src',data.user.head_portrait);
                            $('.nav .login>span:nth-child(1) span').text(data.user.name);
                            /*$('.perinfor-pop .fill>span:nth-child(1) img').attr('src',data.user.head_portrait);
                            $('.perinfor-pop .fill>span:nth-child(1) input').attr('value',data.user.name);
                            $('.perinfor-pop .fill>form #introduction').text(data.user.introduction);*/
                            currentUser=data.user.user_id;
                            console.log(currentUser);
                            $('.login-pop').slideUp();
                        }
                    },
                    error:function(){
                        alert('false');
                    }
                });
            }
        }else {
            // $('.nav .login>span:nth-child(1) img').attr('src','./images/user.png');
            // $('.nav .login>span:nth-child(1) span').text('未登录');
            // $('.login-pop .fill').show();
            // $('.login-pop .user-img').hide();
            // $('.login-pop>button').text('登录');
            // $('.login-pop>button').attr('disabled','');
        }
    });



    //签到
    $('.login-pop .user-img button').click(function () {
        $.ajax({
            url:'/daily_signin',
            type:'get',
            success:function (res) {
                if(res.code==200){
                    $('body').prepend('<div class="tips">' +
                        '<i class="iconfont icon-jifen"></i>' +
                        '<span>积分+'+res.point+'</span>' +
                        '</div>');
                    $('.tips').animate({'opacity':'1'});
                    $('.login-pop .user-img button').text('已签到');
                    $('.login-pop .user-img button').attr('disabled','disabled');
                    $('.login-pop .user-img button').attr('class','detain');
                    setTimeout(function () {
                        $('.tips').animate({'opacity':'0'},function () {
                            $(this).remove();
                        });
                    },2000);
                }else {
                    alert(res.msg);
                }
            }
        });
    });


    //喜欢音乐
    $('.sound span:nth-child(1)').click(function () {
        if (islogin&&likesMusicId.indexOf(lists[musicIndex].id)==-1){
            var app=$(this);
            $.ajax({
                url:'/like?id='+lists[musicIndex].id,
                type:'get',
                success:function (res) {
                    if(res.code==200){
                        app.attr('class','like');
                        app.find('i').attr('class','iconfont icon-like');
                        likesMusicId.push(lists[musicIndex].id);
                    }
                }
            });
        }else {
            var app=$(this);
            if (islogin){
                $.ajax({
                    url:'/like?id='+lists[musicIndex].id+'&like=false',
                    type:'get',
                    success:function (res) {
                        if(res.code==200){
                            app.removeAttr('class');
                            app.find('i').attr('class','iconfont icon-heart2');
                            var i=likesMusicId.indexOf(lists[musicIndex].id)
                            if (i!=-1){
                                likesMusicId.splice(i, 1);
                            }
                        }
                    }
                });
            }
        }
    });

    // 进入歌单
    $('section').on('click','.find .main .recommend .recommend-song>div',function () {
        var id=$(this).attr('data-id');
        $('.loading').show();
        //原生
        // $.ajax({
        //     url:' http://localhost:3000/playlist/detail?id='+id,
        //     type:'get',
        //     success:function (res) {
        //         $.ajax({
        //             url:'cd.html',
        //             type:'get',
        //             success:function (html) {
        //                 history.replaceState({text:$('section').html()}, '');
        //                 $('section').html(html);
        //                 haveLists=res.playlist.tracks;
        //                 $('.author-cd .top>p').text('歌单');
        //                 $('.author-cd .top>div img').attr('src',res.playlist.coverImgUrl);
        //                 $('.author-cd .top>div p:nth-child(2)').text(res.playlist.name);
        //                 var str='';
        //                 for (let i=0;i<res.playlist.tags.length;i++) {
        //                     if (i==res.playlist.tags.length-1) {
        //                         str+=res.playlist.tags[i];
        //                     }else {
        //                         str+=res.playlist.tags[i]+' / ';
        //                     }
        //                 }
        //                 $('.author-cd .top>div p:nth-child(3)').text('标签：'+str);
        //                 $('.author-cd .top>div p:nth-child(4)').text('介绍：'+res.playlist.description);
        //
        //                 var str1='';
        //                 for (let i=0;i<res.playlist.tracks.length;i++){
        //                     str1+='<li>' +
        //                         '<span>'+num(i+1)+'</span>' +
        //                         '<span>'+res.playlist.tracks[i].name+'</span>' +
        //                         '<span>'+res.playlist.tracks[i].ar[0].name+'</span>' +
        //                         '<span>热度：'+res.playlist.tracks[i].pop+'</span>' +
        //                         '</li>'
        //                 }
        //                 $('.author-cd .bottom>ul').html(str1);
        //                 $('.loading').fadeOut();
        //
        //                 history.pushState({}, '', '?songsheetid='+id);
        //                 history.replaceState({text:$('section').html()}, '');
        //             }
        //         });
        //     }
        // });
        //syx
        $.ajax({
            url:'SonglistServelet?method=GetSongListDatabyid',
            type:'post',
            dataType:'json',
            data: {id: id},
            success:function (res) {
                $.ajax({
                    url:'cd.html',
                    type:'get',
                    success:function (html) {
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(html);
                        console.log(res)
                        // haveLists=res.playlist.tracks;
                        $('.author-cd .top>p').text('歌单');
                        $('.author-cd .top>div img').attr('src',res.values[0].picurl);
                        $('.author-cd .top>div p:nth-child(2)').text(res.values[0].songlist_name);
                        var str='';
                        // for (let i=0;i<res.playlist.tags.length;i++) {
                        //     if (i==res.playlist.tags.length-1) {
                        //         str+=res.playlist.tags[i];
                        //     }else {
                        //         str+=res.playlist.tags[i]+' / ';
                        //     }
                        // }
                        $('.author-cd .top>div p:nth-child(3)').text('标签：'+res.values[0].tags);
                        $('.author-cd .top>div p:nth-child(4)').text('介绍：'+res.values[0].description);

                        // var str1='';
                        // for (let i=0;i<res.playlist.tracks.length;i++){
                        //     str1+='<li>' +
                        //         '<span>'+num(i+1)+'</span>' +
                        //         '<span>'+res.playlist.tracks[i].name+'</span>' +
                        //         '<span>'+res.playlist.tracks[i].ar[0].name+'</span>' +
                        //         '<span>热度：'+res.playlist.tracks[i].pop+'</span>' +
                        //         '</li>'
                        // }
                        $('.author-cd .bottom>ul').html(str);
                        $('.loading').fadeOut();

                        history.pushState({}, '', '?songsheetid='+id);
                        history.replaceState({text:$('section').html()}, '');
                    }
                });
                $.ajax({
                    url:'ListsongServelet?method=GetSongListSong',
                    type:'post',
                    dataType:'json',
                    data: {id: id},
                    success:function (res) {
                        // history.replaceState({text:$('section').html()}, '');
                        // $('section').html(html);
                        console.log(res)
                        haveLists=res;
                        // $('.author-cd .top>p').text('歌单');
                        // $('.author-cd .top>div img').attr('src',res.values[0].picurl);
                        // $('.author-cd .top>div p:nth-child(2)').text(res.values[0].songlist_name);
                        var str='';
                        // for (let i=0;i<res.playlist.tags.length;i++) {
                        //     if (i==res.playlist.tags.length-1) {
                        //         str+=res.playlist.tags[i];
                        //     }else {
                        //         str+=res.playlist.tags[i]+' / ';
                        //     }
                        // }
                        // $('.author-cd .top>div p:nth-child(3)').text('标签：'+res.values[0].tags);
                        // $('.author-cd .top>div p:nth-child(4)').text('介绍：'+res.values[0].description);

                        var str1='';
                        for (let i=0;i<res.values.length;i++){
                            str1+='<li>' +
                                '<span>'+num(i+1)+'</span>' +
                                '<span>'+res.values[i].song_name+'</span>' +
                                '<span>'+res.values[i].author_name+'</span>' +
                                '<span>热度：'+res.values[i].pop+'</span>' +
                                '</li>'
                        }
                        $('.author-cd .bottom>ul').html(str1);
                        $('.loading').fadeOut();

                        history.pushState({}, '', '?songsheetid='+id);
                        history.replaceState({text:$('section').html()}, '');
                    }
                });
            }
        });
    });



    // 进入排行榜
    $('section').on('click','.find .main .recommend .ranking>div:nth-child(3)',function () {
        $('.loading').show();
        $.ajax({
            url:'second.html',
            type:'get',
            success:function (html) {
                history.replaceState({text:$('section').html()}, '');
                $('section').html(html);
                getSecond();
                history.pushState({}, '', '?section');
                history.replaceState({text:$('section').html()}, '');
            }
        });
    });

    $('section').on('click','.second .content .divs>div,.second .content .globalDivs>div',function () {
        var idx=$(this).attr('data-idx');
        $('.loading').show();
        $.ajax({
            url:'/top/list?idx='+idx,
            type:'get',
            success:function (res) {
                $.ajax({
                    url:'cd.html',
                    type:'get',
                    success:function (html) {
                        history.replaceState({text:$('section').html()}, '');
                        $('section').html(html);
                        haveLists=res.playlist.tracks;
                        $('.author-cd .top>p').text('排行榜');
                        $('.author-cd .top>div img').attr('src',res.playlist.coverImgUrl);
                        $('.author-cd .top>div p:nth-child(2)').text(res.playlist.name);
                        $('.author-cd .top>div p:nth-child(3)').text('最近更新：'+fmtDate(res.playlist.updateTime));
                        $('.author-cd .top>div p:nth-child(4)').text('介绍：'+res.playlist.description);
                        var str='';
                        for (let i=0;i<res.playlist.tracks.length;i++){
                            str+='<li>' +
                                '<span>'+num(i+1)+'</span>' +
                                '<span>'+res.playlist.tracks[i].name+'</span>' +
                                '<span>'+res.playlist.tracks[i].ar[0].name+'</span>' +
                                '<span>热度：'+res.playlist.tracks[i].pop+'</span>' +
                                '</li>'
                        }
                        $('.author-cd .bottom>ul').html(str);
                        $('.loading').fadeOut();
                        history.pushState({}, '', '?idx='+idx);
                        history.replaceState({text:$('section').html()}, '');
                    }
                });
            }
        });
    });
    
    //进入每日推荐歌曲
    $('section').on('click','.find .main .ranking>div:nth-child(2)',function () {
        $('.loading').show();
       $.ajax({
           url:'recommend.html',
           type:'get',
           success:function (html) {
               history.replaceState({text:$('section').html()}, '');
               $('section').html(html);
               getDayDate();
               getrecommend();
           }
       }); 
    });

    // 推荐歌曲播放音乐
    $('section').on('click','.recommend-songs ul li',function () {
        musicIndex=$(this).index();
        lists=haveLists;
        var str='';
        for (let i=0;i<lists.length;i++){
            str+='<li data-index='+i+'>' +
                '<span>'+lists[i].name+'</span>\n' +
                '<span>'+lists[i].artists[0].name+'</span>\n' +
                '</li>'
        }
        $('.list-pop .list>ul').html(str);
        $('.sound span:nth-child(4) span').text(lists.length);
        $('.recommend-songs ul li span').css('color','');
        $(this).children().css('color','#C32D2E');
        musicInit();
        audio.play();
    });

    //发现页面下导航切换
    $('section').on('click','.find .find-nav ul li',function () {
        var i=$(this).index();
        $(this).attr('class','active').siblings().removeAttr('class');
        $('.find .main>div:eq('+i+')').show().siblings().hide();
        if (i==1){
            findAuthorScrollBottomTest();
            authorOffset=0;
            getAuthors();
        }
    });

    //歌手类别切换
    $('section').on('click','.find .find-author .classify p span',function () {
        $(this).attr('class','active').siblings().removeAttr('class');
        authorOffset=0;
        getAuthors();
    });

    //进入发现音乐下的歌手页
    $('section').on('click','.find .authors>div',function () {
        var id=$(this).attr('data-id');
        authorMvOffset=0;
        $('.loading').show();
        $.ajax({
            url:'author.html',
            type:'get',
            success:function (res) {
                history.replaceState({text:$('section').html()}, '');
                getAuthordetails(id);
                $('section').html(res);
                authorScrollBottomTest();
            }
        });
    });
    
    
    //歌词页面进入专辑和歌手页
    $('.music-pop .lyr .right>p>span:eq(1)>span:eq(1)').click(function () {
        var id=$(this).attr('data-id');
        authorMvOffset=0;
        $('.music-pop').hide();
        $('.loading').show();
        $.ajax({
            url:'author.html',
            type:'get',
            success:function (res) {
                history.replaceState({text:$('section').html()}, '');
                getAuthordetails(id);
                $('section').html(res);
                authorScrollBottomTest();
            }
        });
    });

    $('.music-pop .lyr .right>p>span:eq(1)>span:eq(0)').click(function () {
        var id=$(this).attr('data-id');
        $('.music-pop').hide();
        $('.loading').show();
        getCdDeta(id);
    });




    // mv排行播放mv
    $('section').on('click','.mv-second .paihangbang>div,.mv-second .news>div',function () {
        var id=$(this).attr('data-id');
        getMvUrl(id);
    });


    // mv排行导航切换
    $('section').on('click','.mv-second>ul li',function () {
        $(this).attr('class','active').siblings().removeAttr('class');
        var i=$(this).index();
        $('.mv-second>div').hide();
        $('.mv-second>div:eq('+i+')').show();
        if (i=1){
            $('.ajaxload').show();
            authorMvOffset=0;
            getmvsecond('http://localhost:3000/mv/first?limit=50').then(function (data) {
                $('.mv-second .news').html(data);
                $('.ajaxload').fadeOut();
            });
        }
    });