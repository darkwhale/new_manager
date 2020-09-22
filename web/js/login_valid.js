$(document).ready(function(){
    $('#user_account').keydown(function (e) {
        if(e.which === 13) {
            $('#login_button').trigger("click");
        }
    });

    $('#password').keydown(function (e) {
        if(e.which === 13) {
            $('#login_button').trigger("click");
        }
    });
});

function ajax_login() {
    var user_account = $("#user_account").val();
    var password = $("#password").val();

    if (user_account === "") {
        alert("请输入账号");
    }else if (password === "") {
        alert("请输入密码");
    }

    login(user_account, password);
}

function login(account, password) {
    $.ajax({
        type: "post",
        url: "manage/user/login",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "account": account,
            "password": password
        }),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'user_name=' + message.data.personName;

                    window.location = main_url;
                }else{
                    alert(message.message);
                }
            }
        },
        error: function (message) {
            alert("登录失败")
        }
    });
}
