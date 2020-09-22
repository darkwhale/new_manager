

function authority_add() {
    $.ajax({
        type: "get",
        url: "manage/apply/getApply",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (message) {
            if (message && message.code === 0) {
                var main_body = document.getElementById("right_container");
                main_body.innerHTML = "";

                var authority_table = document.createElement("table");
                authority_table.setAttribute("class", "table table-bordered table-hover table-condensed");
                main_body.appendChild(authority_table);

                create_authority_add_thread(authority_table);

                var authority_table_body = document.createElement("tbody");
                authority_table.appendChild(authority_table_body);

                var bg_color_symbol = true;

                for (let index in message.data) {
                    info = message.data[index];
                    var current_length = create_add_element(authority_table_body, info, bg_color_symbol);
                    bg_color_symbol = !bg_color_symbol;
                }

                if (message.data.length === 0) {
                    main_body.innerHTML = "";
                    var prompt_info = document.createElement("blockquote");

                    var prompt_info_p = document.createElement("p");
                    prompt_info_p.innerText = "暂时没有有权限申请";

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

function create_authority_add_thread(container) {

    var authority_thread = document.createElement("thead");
    container.appendChild(authority_thread);

    var authority_thread_tr = document.createElement("tr");
    authority_thread.appendChild(authority_thread_tr);

    add_element(authority_thread_tr, "用户账号");
    add_element(authority_thread_tr, "用户名");
    add_element(authority_thread_tr, "用户部门");
    add_element(authority_thread_tr, "应用");
    add_element(authority_thread_tr, "有效期");
    add_element(authority_thread_tr, "申请时间");

    var authority_action = document.createElement("td");
    authority_action.innerText = "操作";
    authority_action.colSpan = 2;
    // authority_action.setAttribute("width", "10%");
    // console.log(authority_action.getAttribute("width"));
    authority_action.setAttribute("align", "center");
    authority_thread_tr.appendChild(authority_action);
}

function create_add_element(container, sub_info, bg_color) {

    var sub_apply_id = info.applyId;

    var sub_account = info.account;
    var sub_personName = info.personName;
    var sub_apartment = info.apartment;
    var sub_application_name = info.applicationName;
    var sub_legal_time = info.legalTime;
    var sub_time = info.time;

    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);

    if (bg_color) {
        sub_tr.setAttribute("class", "success");
    }

    add_element(sub_tr, sub_account);
    add_element(sub_tr, sub_personName);
    add_element(sub_tr, sub_apartment);
    add_element(sub_tr, sub_application_name);
    add_element(sub_tr, sub_legal_time);
    add_element(sub_tr, sub_time);

    var sub_td_ok = document.createElement("td");
    sub_td_ok.setAttribute("align", "center");
    var sub_td_ok_a = document.createElement("a");
    sub_td_ok_a.href = "javascript:void(0)";
    sub_td_ok_a.innerText = "通过";
    sub_td_ok_a.onclick = function () {
        authority_ok(sub_apply_id);
    };
    sub_td_ok.appendChild(sub_td_ok_a);
    sub_tr.appendChild(sub_td_ok);

    var sub_td_denied = document.createElement("td");
    sub_td_denied.setAttribute("align", "center");
    var sub_td_denied_a = document.createElement("a");
    sub_td_denied_a.href = "javascript:void(0)";
    sub_td_denied_a.innerText = "驳回";
    sub_td_denied_a.onclick = function () {
        authority_denied(sub_apply_id);
    };
    sub_td_denied.appendChild(sub_td_denied_a);
    sub_tr.appendChild(sub_td_denied);

}

function authority_ok(apply_id) {
    var delete_symbol = window.confirm("确定要通过该权限申请吗?");
    if (delete_symbol) {
        $.ajax({
            type: "post",
            url: "manage/apply/ok",
            contentType: "application/x-www-form-urlencoded",
            data: {
                "applyId": apply_id
            },
            dataType: "json",
            success: function (message) {
                if (message && message.code === 0) {
                    authority_add();
                }else {
                    alert("用户未授权或其他错误");
                }
            }
        });
    }
}

function authority_denied(apply_id) {
    var delete_symbol = window.confirm("确定要拒绝该权限申请吗?");
    if (delete_symbol) {
        $.ajax({
            type: "post",
            url: "manage/apply/denied",
            contentType: "application/x-www-form-urlencoded",
            data: {
                "applyId": apply_id
            },
            dataType: "json",
            success: function (message) {
                if (message && message.code === 0) {
                    authority_add();
                }else {
                    alert("用户未授权或其他错误");
                }
            }
        });
    }
}
