$(document).ready(function(){
    $('#user_id').keydown(function (e) {
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
    var user_id = $("#user_id").val();
    var password = $("#password").val();

    if (user_id === "") {
        alert("请输入id");
    }else if (password === "") {
        alert("请输入密码");
    }

    login(user_id, password);
}

function login(id, password) {
    $.ajax({
        type: "post",
        url: "manage/manager/login",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"managerId": id, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'manager_name=' + message.data.managerName;

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
