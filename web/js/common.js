
function getCookie(name)
{
    var start;
    var end;
    if (document.cookie.length > 0) {
        start = document.cookie.indexOf(name + "=");
        if (start !== -1) {
            start = start + name.length + 1;
            end = document.cookie.indexOf(";", start);
            if (end === -1){
                end = document.cookie.length;
            }
            return document.cookie.substring(start, end);
        }
    }
    return ""
}

function GetRequest(name) {
    var url = location.search;
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}


function clear_cache() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if(keys) {
        for(var i = keys.length; i--;) {
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
        }
    }
}

function get_user_app_list() {
    var legal_user_app_list = [];
    $.ajax({
        type: "get",
        async: false,
        url: "manage/user/userInfo",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    user_app_list = message.data.appList;

                    for (let index in user_app_list) {
                        info = user_app_list[index];

                        if (info.legalTime !== 0) {

                            legal_user_app_list[info.applicationId] = info;
                        }
                    }

                }else{
                    // alert(message.message);
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return legal_user_app_list;
}

function add_element(container, value, width) {
    var sub_td = document.createElement("td");
    sub_td.innerText = value;
    sub_td.setAttribute("align", "center");
    if (width !== undefined) {
        sub_td.setAttribute("width", width);
    }

    container.appendChild(sub_td);
}



var main_url = "http://127.0.0.1";
var app_root_url = "http://127.0.0.1/manage/app";
var login_url = main_url + "/login.html";
var app_url = main_url + "/app_info.html";
var apply_url = main_url + "/apply.html";
