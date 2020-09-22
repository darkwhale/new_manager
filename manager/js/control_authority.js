
$(document).ready(function(){
    var manager_name = getCookie("manager_name");
    if (manager_name === "") {
        window.location = login_url;
    }else {
        $("#username").text(manager_name);
        $("#login").hide();
        $("#logout").text("退出");
    }

    authority_list();
});