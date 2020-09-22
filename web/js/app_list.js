
function get_app_list() {
    var user_name = getCookie("user_name");
    var legal_user_app_list = [];
    if (user_name !== "") {
        legal_user_app_list = get_user_app_list();
    }

    var app_list = get_all_app_list();

    var main_body = document.getElementById("main_container");
    main_body.innerHTML = "";

    if (app_list.length === 0) {

        var prompt_info = document.createElement("blockquote");

        var prompt_info_p = document.createElement("p");
        prompt_info_p.innerText = "暂时还未添加任何应用";

        var prompt_info_small = document.createElement("small");
        prompt_info_small.innerText = "一位善意的智者";

        prompt_info.appendChild(prompt_info_p);
        prompt_info.appendChild(prompt_info_small);

        main_body.appendChild(prompt_info);
    }else {
        var app_table = document.createElement("table");
        app_table.setAttribute("class", "table table-bordered table-hover table-condensed");
        main_body.appendChild(app_table);

        create_app_thread(app_table);

        var app_table_body = document.createElement("tbody");
        app_table.appendChild(app_table_body);

        var bg_color_symbol = true;
        for (let index in app_list) {

            var info = app_list[index];

            // 判断是否已有权限
            var application_id = info.applicationId;
            if (legal_user_app_list[application_id] === undefined) {
                create_tr_element(app_table_body, info, true, bg_color_symbol);
            }else {
                create_tr_element(app_table_body, info, false, bg_color_symbol);
            }
            bg_color_symbol = !bg_color_symbol;

        }
    }
}


function get_all_app_list() {
    var app_list = [];
    $.ajax({
        type: "get",
        async: false,
        url: "manage/application/getAll",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    app_list = message.data;

                }else{
                    alert(message.message);
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return app_list;
}


function  create_app_thread(container) {
    var app_thread = document.createElement("thead");
    container.appendChild(app_thread);

    var app_thread_tr = document.createElement("tr");
    app_thread.appendChild(app_thread_tr);

    add_element(app_thread_tr, "应用名");
    add_element(app_thread_tr, "应用网址");
    add_element(app_thread_tr, "备注");
    add_element(app_thread_tr, "api注册数量");
    add_element(app_thread_tr, "操作");
}

function create_tr_element(container, info, apply_symbol, bg_color) {
    var sub_application_id = info.applicationId;
    var sub_app_name = info.applicationName;
    var sub_url = app_root_url + "/" + info.applicationName;
    var sub_remark = info.remark;
    var sub_api_list = info.apiList;

    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);

    if (bg_color) {
        sub_tr.setAttribute("class", "success");
    }

    var sub_td_name = document.createElement("td");
    sub_td_name.setAttribute("align", "center");
    var sub_td_name_a = document.createElement("a");
    sub_td_name_a.href = "javascript:void(0)";
    sub_td_name_a.innerText = sub_app_name;
    sub_td_name_a.onclick = function () {
        app_info_index(sub_application_id);
    };
    sub_td_name.appendChild(sub_td_name_a);
    sub_tr.appendChild(sub_td_name);

    add_element(sub_tr, sub_url);
    add_element(sub_tr, sub_remark);
    add_element(sub_tr, sub_api_list.length);

    var sub_td_apply = document.createElement("td");
    sub_td_apply.setAttribute("align", "center");

    if (apply_symbol) {
        var sub_td_apply_a = document.createElement("a");
        sub_td_apply_a.href = "javascript:void(0)";
        sub_td_apply_a.innerText = "申请";
        sub_td_apply_a.onclick = function () {
            apply(sub_application_id);
        };
        sub_td_apply.appendChild(sub_td_apply_a);
    }else {
        sub_td_apply.innerText = "申请";
    }

    sub_tr.appendChild(sub_td_apply);

}

function app_info_index(app_id) {
    window.location = app_url + "?app_id=" + app_id;
}

function apply(app_id) {
    window.location = apply_url + "?app_id=" + app_id;
}