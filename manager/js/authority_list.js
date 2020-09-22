
function authority_list() {

    $.ajax({
        type: "get",
        url: "manage/user/getAll",
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
                var total_element = 0;
                for (index in message.data) {
                    info = message.data[index];
                    var current_length = create_element(authority_table_body, info, bg_color_symbol);
                    if (current_length !== 0) {
                        bg_color_symbol = !bg_color_symbol;

                    }
                    total_element += current_length;
                }
                if (total_element === 0) {
                    main_body.innerHTML = "";
                    var prompt_info = document.createElement("blockquote");

                    var prompt_info_p = document.createElement("p");
                    prompt_info_p.innerText = "暂时还未授予任何权限";

                    var prompt_info_small = document.createElement("small");
                    prompt_info_small.innerText = "一位善意的智者";

                    prompt_info.appendChild(prompt_info_p);
                    prompt_info.appendChild(prompt_info_small);

                    main_body.appendChild(prompt_info);
                }
            }else {
                alert("管理员已退出登录或其他错误");
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

    add_element(authority_thread_tr, "用户账号", "10%");
    add_element(authority_thread_tr, "用户名", "10%");
    add_element(authority_thread_tr, "用户部门", "10%");
    add_element(authority_thread_tr, "应用", "10%");
    add_element(authority_thread_tr, "有效期", "10%");
    add_element(authority_thread_tr, "操作", "10%");

}

function create_element(container, info, bg_color) {
    var sub_userId = info.userId;
    var sub_account = info.account;
    var sub_personName = info.personName;
    var sub_apartment = info.apartment;

    var app_list = info.appList;
    var sub_length = 0;

    for(sub_index in app_list) {
        app_info_ = app_list[sub_index];
        sub_length += create_sub_element(container, app_info_, bg_color, sub_userId, sub_account, sub_personName, sub_apartment);
    }

    return sub_length;
}


function create_sub_element(container, app_info, bg_color, sub_userId, sub_account, sub_personName, sub_apartment) {

    let sub_app = app_info.applicationName;
    let sub_legal_time = app_info.legalTime;
    let sub_applicationId = app_info.applicationId;

    if (sub_legal_time === 0) {
        return 0;
    }

    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);
    if (bg_color) {
        sub_tr.setAttribute("class", "success");
    }

    add_element(sub_tr, sub_account);
    add_element(sub_tr, sub_personName);
    add_element(sub_tr, sub_apartment);
    add_element(sub_tr, sub_app);

    // console.log(sub_legal_time, parseInt(sub_legal_time / 86400) + 1);
    add_element(sub_tr, parseInt(sub_legal_time / 86400) + 1);
    // add_element(sub_tr, sub_legal_time);

    var sub_td_delete = document.createElement("td");
    sub_td_delete.setAttribute("align", "center");
    var sub_td_delete_a = document.createElement("a");
    sub_td_delete_a.href = "javascript:void(0)";
    sub_td_delete_a.innerText = "删除";
    sub_td_delete_a.onclick = function () {
        delete_authority(sub_userId, sub_applicationId);
    };
    sub_td_delete.appendChild(sub_td_delete_a);
    sub_tr.appendChild(sub_td_delete);

    return 1;
}


function delete_authority(user_id, application_id) {
    var delete_symbol = window.confirm("确定要删除吗?");
    if (delete_symbol) {
        $.ajax({
            type: "post",
            url: "manage/authority/deleteByUser",
            contentType: "application/x-www-form-urlencoded",
            data: {
                "userId": user_id,
                "applicationId": application_id
            },
            dataType: "json",
            success: function (message) {
                if (message && message.code === 0) {
                    authority_list();
                }else {
                    alert("用户未授权或其他错误");
                }
            }
        });
    }
}
