
function app_list() {
    $.ajax({
        type: "get",
        url: "manage/application/getAll",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (message) {
            if (message && message.code === 0) {
                var main_body = document.getElementById("right_container");
                main_body.innerHTML = "";

                var authority_table = document.createElement("table");
                authority_table.setAttribute("class", "table table-bordered table-hover table-condensed");
                main_body.appendChild(authority_table);

                create_authority_thread(authority_table);

                var authority_table_body = document.createElement("tbody");
                authority_table.appendChild(authority_table_body);

                var bg_color_symbol = true;
                for (let index in message.data) {
                    info = message.data[index];
                    create_element(authority_table_body, info, bg_color_symbol);
                    bg_color_symbol = !bg_color_symbol;

                }
                if (message.data.length === 0) {
                    main_body.innerHTML = "";
                    var prompt_info = document.createElement("blockquote");

                    var prompt_info_p = document.createElement("p");
                    prompt_info_p.innerText = "暂时还未添加任何应用";

                    var prompt_info_small = document.createElement("small");
                    prompt_info_small.innerText = "一位善意的智者";

                    prompt_info.appendChild(prompt_info_p);
                    prompt_info.appendChild(prompt_info_small);

                    main_body.appendChild(prompt_info);
                }
            }else {
                alert(message.message);
                clear_cache();
                window.location = login_url;
            }
        }
    });

}

function create_authority_thread(container) {

    var authority_thread = document.createElement("thead");
    container.appendChild(authority_thread);

    var authority_thread_tr = document.createElement("tr");
    authority_thread.appendChild(authority_thread_tr);

    add_element(authority_thread_tr, "应用名");
    add_element(authority_thread_tr, "应用网址");
    add_element(authority_thread_tr, "备注");
    add_element(authority_thread_tr, "api注册数量");

    var authority_action = document.createElement("td");
    authority_action.innerText = "操作";
    authority_action.colSpan = 2;
    // authority_action.setAttribute("width", "10%");
    // console.log(authority_action.getAttribute("width"));
    authority_action.setAttribute("align", "center");
    authority_thread_tr.appendChild(authority_action);
}

function create_element(container, info, bg_color) {

    var sub_application_id = info.applicationId;
    var sub_app_name = info.applicationName;
    var sub_url = info.url;
    var sub_remark = info.remark;
    var sub_api_list = info.apiList;

    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);

    if (bg_color) {
        sub_tr.setAttribute("class", "success");
    }

    // add_element(sub_tr, sub_app_name);
    var sub_td_name = document.createElement("td");
    sub_td_name.setAttribute("align", "center");
    var sub_td_name_a = document.createElement("a");
    sub_td_name_a.href = "javascript:void(0)";
    sub_td_name_a.innerText = sub_app_name;
    sub_td_name_a.onclick = function () {
        app_info(sub_application_id);
    };
    sub_td_name.appendChild(sub_td_name_a);
    sub_tr.appendChild(sub_td_name);

    add_element(sub_tr, sub_url);
    add_element(sub_tr, sub_remark);
    add_element(sub_tr, sub_api_list.length);

    var sub_td_ok = document.createElement("td");
    sub_td_ok.setAttribute("align", "center");
    var sub_td_ok_a = document.createElement("a");
    sub_td_ok_a.href = "javascript:void(0)";
    sub_td_ok_a.innerText = "编辑";
    sub_td_ok_a.onclick = function () {
        app_add_edit(sub_application_id, sub_app_name, sub_url, sub_remark);
    };
    sub_td_ok.appendChild(sub_td_ok_a);
    sub_tr.appendChild(sub_td_ok);

    var sub_td_delete = document.createElement("td");
    sub_td_delete.setAttribute("align", "center");
    var sub_td_delete_a = document.createElement("a");
    sub_td_delete_a.href = "javascript:void(0)";
    sub_td_delete_a.innerText = "删除";
    sub_td_delete_a.onclick = function () {
        application_delete(sub_application_id);
    };
    sub_td_delete.appendChild(sub_td_delete_a);
    sub_tr.appendChild(sub_td_delete);
}

function application_delete(application_id) {
    var delete_symbol = window.confirm("确定要删除该应用吗?");
    if (delete_symbol) {
        $.ajax({
            type: "post",
            url: "manage/application/delete",
            contentType: "application/x-www-form-urlencoded",
            data: {
                "applicationId": application_id
            },
            dataType: "json",
            success: function (message) {
                if (message && message.code === 0) {
                    app_list();
                }else {
                    alert(message.message);
                }
            }
        });
    }
}

function app_info(app_id) {
    window.location = app_url + "?app_id=" + app_id;
}
