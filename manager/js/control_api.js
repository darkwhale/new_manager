
$(document).ready(function(){
    var manager_name = getCookie("manager_name");
    if (manager_name === "") {
        window.location = login_url;
    }else {
        $("#username").text(manager_name);
        $("#login").hide();
        $("#logout").text("退出");
    }

    api_list();
});

function api_change(application_id, api_id, api, api_name, api_remark) {

    var main_body = document.getElementById("right_container");
    main_body.innerHTML = "";

    var parent_div = document.createElement("div");
    main_body.appendChild(parent_div);

    add_api_element(parent_div, "接口", api, "api_name");
    add_api_element(parent_div, "接口名", api_name, "api_url");
    add_api_element(parent_div, "详细说明", api_remark, "api_remark");

    var submit_button = document.createElement("button");
    submit_button.setAttribute("class", "btn btn-default");
    submit_button.textContent = "提交";
    submit_button.onclick = function () {
        post_api(application_id, api_id);
    };
    parent_div.appendChild(submit_button);
}

function add_api_element(container, label, value, label_id) {

    var sub_div = document.createElement("div");
    sub_div.setAttribute("class", "form-group");
    container.appendChild(sub_div);

    var sub_label = document.createElement("label");
    sub_label.setAttribute("for", label_id);
    sub_label.innerText = label;

    sub_div.appendChild(sub_label);

    if (label_id === "api_remark") {
        sub_input = document.createElement("textarea");
        sub_input.setAttribute("rows", 10);
    }else {
        sub_input = document.createElement("input");
        sub_input.type = "text";
    }

    sub_input.id = label_id;
    sub_input.value = value;
    sub_input.setAttribute("class", "form-control");

    sub_div.appendChild(sub_input);

}


function post_api(application_id, api_id) {
    if (api_id === null) {
        dest_url = "add";
    }else {
        dest_url = "update";
    }

    var api_name = document.getElementById("api_name").value;
    var api_url = document.getElementById("api_url").value;
    var api_remark = document.getElementById("api_remark").value;

    $.ajax({
        type: "post",
        url: "manage/api/" + dest_url,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "applicationId": application_id,
            "apiId": api_id,
            "apiValue": api_name,
            "apiName": api_url,
            "remark": api_remark}),
        dataType: "json",

        success: function (message) {
            if (message && message.code === 0) {
                api_list();
            }else {
                alert(message.message);
            }
        }
    });
}