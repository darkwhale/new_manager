
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

function add_element(container, value, width) {
    var sub_td = document.createElement("td");
    sub_td.innerText = value;
    sub_td.setAttribute("align", "center");
    if (width !== undefined) {
        sub_td.setAttribute("width", width);
    }

    container.appendChild(sub_td);
}


var main_url = "http://127.0.0.1:9002";
var login_url = main_url + "/login.html";
var app_url = main_url + "/app_info.html";
