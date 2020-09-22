
$(document).ready(function(){
    var user_name = getCookie("user_name");
    if (user_name === "") {
        $("#username").hide();
        $("#login").text("登录");
        $("#register").text("注册");
        $("#logout").hide();
    }else {
        $("#username").text(user_name);
        $("#login").hide();
        $("#logout").text("退出");
    }

    authority_list();
});