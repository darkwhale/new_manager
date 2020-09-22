
function ajax_logout() {
    clear_cache();
    // window.location="http://127.0.0.1";
    $.ajax({
        type: "post",
        url: "manage/user/logout",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    window.location = main_url;
                }
            }else{
                alert(message.message);
            }
        },

    });
    window.location = main_url;
}
