
function ajax_register() {
    var account = $("#user_account").val();
    var apartment = $("#user_apartment").val();
    var username = $("#user_name").val();
    var password = $("#password").val();

    if (account === "") {
        alert("请输入账号");
    }else if(apartment ===""){
        alert("请输入部门");
    }else if (username === "") {
        alert("请输入姓名");
    }else if(password === "") {
        alert("请输入密码");
    }

    $.ajax({
        type: "post",
        url: "manage/user/register",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "account": account,
            "apartment": apartment,
            "password": password,
            "personName": username
        }),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 登录
                    login(account, password);

                }else{
                    alert(message.message);
                }
            }else {
                alert("数据错误")
            }
        },

    });

}
