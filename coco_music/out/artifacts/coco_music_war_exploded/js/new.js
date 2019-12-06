// 点设置弹出个人信息弹框
$('.nav .user .login>span:nth-child(3)').click(function () {
    // $('.perinfor-pop').stop().slideToggle();
    $('.perinfor-pop').stop().animate({width: 'toggle'});
});
// 个人信息页面退出登录
$('.perinfor-pop>#quit1').click(function () {
    $('.nav .login>span:nth-child(1) img').attr('src','images/user.png');
    $('.nav .login>span:nth-child(1) span').text("未登录");
    $('.perinfor-pop').animate({width: 'toggle'});
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