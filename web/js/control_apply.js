
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

    apply();
});

function apply() {
    var app_id = GetRequest()["app_id"];
    $.ajax({
        type: "get",
        async: false,
        url: "manage/application/findByApp",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "applicationId": app_id
        },

        success: function(message){
            if (message){
                if (message.code === 0) {
                    var app_name = message.data.applicationName;
                    var app_url = app_root_url + "/" + app_name;
                    var app_remark = message.data.remark;

                    var app_name_input = document.getElementById("app_name");
                    var app_url_input = document.getElementById("app_url");
                    var app_remark_input = document.getElementById("app_remark");

                    app_name_input.value = app_name;
                    app_url_input.value = app_url;
                    app_remark_input.value = app_remark;

                }else{
                    alert(message.message);
                }
            }else {
                alert("数据错误")
            }
        },
    });
}

function post_apply() {
    var app_id = GetRequest()["app_id"];

    var legal_time = $("#app_legal_time").val();
    $.ajax({
        type: "post",
        url: "manage/apply/apply",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "applicationId": app_id,
            "legalTime": legal_time
        },
        success: function(message){
            if (message){
                if (message.code === 0) {
                    alert("申请成功");
                    window.location = main_url;
                }else{
                    alert(message.message);
                }
            }else {
                alert("数据错误")
            }
        },
    });
}
