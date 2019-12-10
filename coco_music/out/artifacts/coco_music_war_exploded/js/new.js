var currentUser=null;
//时间转化函数
function timestampToTime(timestamp) {
    var date = new Date(timestamp);//time
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
    return Y+M+D;
}

function getCurrentUserInformation() {
    var datas={
        user_id:currentUser
    };
    $.ajax({
        url:'UserInforServlet?method=getAllInf',
        type:'post',
        dataType:'json',
        data:datas,
        success:function(data){
            console.log(data);
            $('.perinfor-pop .fill>span:nth-child(1) img').attr('src',data.user.head_portrait);
            // $('.perinfor-pop .fill>span:nth-child(1) input').attr('value',data.user.name);
            $('.perinfor-pop .fill>span:nth-child(1) input').val(data.user.name);
            if(data.user.sex=="男"){
                // $('.perinfor-pop .fill>form #sex').text(data.user.sex);
                $(".perinfor-pop .fill>form #sex").val("2");
            }else if(data.user.sex=="女"){
                $(".perinfor-pop .fill>form #sex").val("3");
            }else{
                $(".perinfor-pop .fill>form #sex").val("1");
            }
            /*var d=timestampToTime(data.birthday);
            var d2=timestampToTime(data.birthday2);*/
            var birthday=timestampToTime(data.user.birthday);
            // console.log(d);
            // console.log(d2);
            // console.log(birthday);
            $('.perinfor-pop .fill>form #birthday').val(birthday);
            // $('.perinfor-pop .fill>form #birthday').val(data.birthday);
            // console.log(data.birthday);
            $('.perinfor-pop .fill>form #introduction').val(data.user.introduction);
        }
    });
}

function hidebutton(){
    $('.perinfor-pop .fill input').attr('disabled',false);
    $('.perinfor-pop .fill select').attr('disabled',false);
    $('.perinfor-pop .fill textarea').attr('disabled',false);
    $('.perinfor-pop>#cancel').show();
    $('.perinfor-pop>#verify').show();
    $('.perinfor-pop>#modifyinfor').hide();
    $('.perinfor-pop>#modifypass').hide();
    $('.perinfor-pop>#quit1').hide();
}

function showbutton(){
    $('.perinfor-pop .fill input').attr('disabled',true);
    $('.perinfor-pop .fill select').attr('disabled',true);
    $('.perinfor-pop .fill textarea').attr('disabled',true);
    $('.perinfor-pop>#cancel').hide();
    $('.perinfor-pop>#verify').hide();
    $('.perinfor-pop>#modifyinfor').show();
    $('.perinfor-pop>#modifypass').show();
    $('.perinfor-pop>#quit1').show();
}
// 点设置弹出个人信息弹框
$('.nav .user .login>span:nth-child(3)').click(function () {
    // $('.perinfor-pop').stop().slideToggle();
    if(islogin==true) {
        getCurrentUserInformation();
        /*var datas={
            user_id:currentUser
        };
        $.ajax({
            url:'UserInforServlet?method=getAllInf',
            type:'post',
            dataType:'json',
            data:datas,
            success:function(data){
                console.log(data);
                $('.perinfor-pop .fill>span:nth-child(1) img').attr('src',data.user.head_portrait);
                $('.perinfor-pop .fill>span:nth-child(1) input').attr('value',data.user.name);
                if(data.user.sex=="男"){
                    // $('.perinfor-pop .fill>form #sex').text(data.user.sex);
                    $(".perinfor-pop .fill>form #sex").val("2");
                }else if(data.user.sex=="女"){
                    $(".perinfor-pop .fill>form #sex").val("3");
                }else{
                    $(".perinfor-pop .fill>form #sex").val("1");
                }

                // $('.perinfor-pop .fill>form #birthday').text(data.user.birthday);
                $('.perinfor-pop .fill>form #introduction').text(data.user.introduction);
            }
            /!*            error:function(){
                            alert('false');
                        }*!/
        });*/
        $('.perinfor-pop').stop().animate({width: 'toggle'});
    }else{
        alert("请登录");
    }
});
//个人页面关闭按钮
$('.perinfor-pop .fill>#next').click(function () {
    $('.perinfor-pop').stop().animate({width: 'toggle'});
});
// 个人信息页面退出登录
$('.perinfor-pop>#quit1').click(function () {
    $('.nav .login>span:nth-child(1) img').attr('src','images/user.png');
    $('.nav .login>span:nth-child(1) span').text("未登录");
    $('.perinfor-pop').animate({width: 'toggle'});
    islogin=false;
});
// 设置按钮动画
$('.perinfor-pop').on('mouseover','#quit1',function () {
    $('.perinfor-pop>#quit1').css('background-color','#3f51b5');
    $('.perinfor-pop>#quit1').css('color','#ffffff');
}).on('mouseleave','#quit1',function () {
    $('.perinfor-pop>#quit1').css('background-color','#ffffff');
    $('.perinfor-pop>#quit1').css('color','#000000');
});
$('.perinfor-pop').on('mouseover','#modifyinfor',function () {
    $('.perinfor-pop>#modifyinfor').css('background-color','#3f51b5');
    $('.perinfor-pop>#modifyinfor').css('color','#ffffff');
}).on('mouseleave','#modifyinfor',function () {
    $('.perinfor-pop>#modifyinfor').css('background-color','#ffffff');
    $('.perinfor-pop>#modifyinfor').css('color','#000000');
});
$('.perinfor-pop').on('mouseover','#modifypass',function () {
    $('.perinfor-pop>#modifypass').css('background-color','#3f51b5');
    $('.perinfor-pop>#modifypass').css('color','#ffffff');
}).on('mouseleave','#modifypass',function () {
    $('.perinfor-pop>#modifypass').css('background-color','#ffffff');
    $('.perinfor-pop>#modifypass').css('color','#000000');
});
$('.perinfor-pop').on('mouseover','#verify',function () {
    $('.perinfor-pop>#verify').css('background-color','#3f51b5');
    $('.perinfor-pop>#verify').css('color','#ffffff');
}).on('mouseleave','#verify',function () {
    $('.perinfor-pop>#verify').css('background-color','#ffffff');
    $('.perinfor-pop>#verify').css('color','#000000');
});
$('.perinfor-pop').on('mouseover','#cancel',function () {
    $('.perinfor-pop>#cancel').css('background-color','#3f51b5');
    $('.perinfor-pop>#cancel').css('color','#ffffff');
}).on('mouseleave','#cancel',function () {
    $('.perinfor-pop>#cancel').css('background-color','#ffffff');
    $('.perinfor-pop>#cancel').css('color','#000000');
});
// 个人信息页面修改信息
$('.perinfor-pop>#modifyinfor').click(function () {
    hidebutton();
    /*$('.perinfor-pop .fill input').attr('disabled',false);
    $('.perinfor-pop .fill select').attr('disabled',false);
    $('.perinfor-pop .fill textarea').attr('disabled',false);
    $('.perinfor-pop>#cancel').show();
    $('.perinfor-pop>#verify').show();
    $('.perinfor-pop>#modifyinfor').hide();
    $('.perinfor-pop>#modifypass').hide();
    $('.perinfor-pop>#quit1').hide();*/
});
// 取消修改个人信息
$('.perinfor-pop>#cancel').click(function () {
    getCurrentUserInformation();
    showbutton();
    /*$('.perinfor-pop .fill input').attr('disabled',true);
    $('.perinfor-pop .fill select').attr('disabled',true);
    $('.perinfor-pop .fill textarea').attr('disabled',true);
    $('.perinfor-pop>#cancel').hide();
    $('.perinfor-pop>#verify').hide();
    $('.perinfor-pop>#modifyinfor').show();
    $('.perinfor-pop>#modifypass').show();
    $('.perinfor-pop>#quit1').show();*/
});
// 个人信息页面修改信息确认按钮
$('.perinfor-pop>#verify').click(function () {
    var $name=$('.perinfor-pop .fill>span:nth-child(1) input').val();
    var $sex=$(".perinfor-pop .fill>form #sex").find("option:selected").text();
    var $birthday=$('.perinfor-pop .fill>form #birthday').val();
    var $introduction=$('.perinfor-pop .fill>form #introduction').val();
    if($name==''){
        alert("用户名不能为空");
        return false;
    }
    else{
        var datas={
            user_id:currentUser,
            name:$name,
            sex:$sex,
            birthday:$birthday,
            introduction:$introduction
        };
        console.log(datas);
        $.ajax({
            url:'UserInforServlet?method=changeInfor',
            type:'post',
            dataType:'json',
            data:datas,
            success:function(data){
                if("error" == data.type){
                    alert(data.msg);
                }else if("success" == data.type){
                    alert('修改成功');
                    showbutton();
                    getCurrentUserInformation();
                }
            },
            error:function(){
                alert('false');
            }
        });
    }
});
//关闭改密码的框
$('.changepwd-pop .close').click(function () {
    $('.changepwd-pop').slideUp();
});
//点击修改密码，弹出修改框
$('.perinfor-pop>#modifypass').click(function () {
    $('.perinfor-pop').animate({width: 'toggle'});
    $('.changepwd-pop').stop().slideToggle();
});
// 修改密码按钮操作
$('.changepwd-pop>button').click(function () {
    if ($(this).text()=='确定'){
        var $pass=$('.changepwd-pop .pass input').val();
        var $newpass=$('.changepwd-pop .newpass input').val();
        var $newpass2=$('.changepwd-pop .newpass2 input').val();
        if($pass==''){
            $('.changepwd-pop .prompt>span').text('原密码不能为空');
            $('.changepwd-pop .prompt').show();
            return false;
        }
        else if($newpass==''){
            $('.changepwd-pop .prompt>span').text('新密码不能为空');
            $('.changepwd-pop .prompt').show();
            return false;
        }
        else if($newpass!=$newpass2){
            // alert("两次密码不一致");
            $('.changepwd-pop .prompt>span').text('两次密码不一致');
            $('.changepwd-pop .prompt').show();
            return false;
        } else{
            var datas={
                user_id:currentUser,
                password:$pass,
                newpassword:$newpass
            };
            console.log(datas);
            $.ajax({
                url:'UserInforServlet?method=changePwd',
                type:'post',
                dataType:'json',
                data:datas,
                success:function(data){
                    if("error" == data.type){
                        $('.changepwd-pop .prompt>span').text(data.msg);
                        $('.changepwd-pop .prompt').show();
                    }else if("success" == data.type){
                        alert('修改成功，请重新登录');
                        $('.changepwd-pop').slideUp();
                        $('.nav .login>span:nth-child(1) img').attr('src','images/user.png');
                        $('.nav .login>span:nth-child(1) span').text("未登录");
                        islogin=false;
                    }
                },
                error:function(){
                    alert('false');
                }
            });
        }
    }else {
        $('.changepwd-pop').slideUp();
    }
});

// 搜索页面搜到歌单后进入歌单
$('section').on('click','.search .searchBody .songList li',function () {
    var id=$(this).attr('data-id');
    $('.loading').show();
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
                    $('.author-cd .top>p').text('歌单');
                    $('.author-cd .top>div img').attr('src',res.values[0].picurl);
                    $('.author-cd .top>div p:nth-child(2)').text(res.values[0].songlist_name);
                    var str='';
                    $('.author-cd .top>div p:nth-child(3)').text('标签：'+res.values[0].tags);
                    $('.author-cd .top>div p:nth-child(4)').text('介绍：'+res.values[0].description);
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
                    console.log(res)
                    haveLists=res;
                    var str='';
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
$(function(){
    //得到当前时间
    var date_now = new Date();
    //得到当前年份
    var year = date_now.getFullYear();
    //得到当前月份
    //注：
    //  1：js中获取Date中的month时，会比当前月份少一个月，所以这里需要先加一
    //  2: 判断当前月份是否小于10，如果小于，那么就在月份的前面加一个 '0' ， 如果大于，就显示当前月份
    var month = date_now.getMonth()+1 < 10 ? "0"+(date_now.getMonth()+1) : (date_now.getMonth()+1);
    //得到当前日子（多少号）
    var date = date_now.getDate() < 10 ? "0"+date_now.getDate() : date_now.getDate();
    //设置input标签的max属性
    $("#birthday").attr("max",year+"-"+month+"-"+date);
})