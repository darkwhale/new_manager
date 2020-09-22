
$(document).ready(function(){
    var manager_name = getCookie("manager_name");
    if (manager_name === "") {
        window.location = login_url;
    }else {
        $("#username").text(manager_name);
        $("#login").hide();
        $("#logout").text("退出");
    }

    app_list();
});

function app_add_edit(app_id, app_name, app_url, app_remark) {

    var main_body = document.getElementById("right_container");
    main_body.innerHTML = "";

    var parent_div = document.createElement("div");
    main_body.appendChild(parent_div);

    add_app_element(parent_div, "应用名", app_name, "app_name");
    add_app_element(parent_div, "应用接口", app_url, "app_url");
    add_app_element(parent_div, "说明", app_remark, "app_remark");

    var submit_button = document.createElement("button");
    submit_button.setAttribute("class", "btn btn-default");
    submit_button.textContent = "提交";
    submit_button.onclick = function () {
        post_app(app_id);
    };
    parent_div.appendChild(submit_button);


}

function add_app_element(container, label, value, label_id) {

    var sub_div = document.createElement("div");
    sub_div.setAttribute("class", "form-group");
    container.appendChild(sub_div);

    var sub_label = document.createElement("label");
    sub_label.setAttribute("for", label_id);
    sub_label.innerText = label;

    sub_div.appendChild(sub_label);

    var sub_input = document.createElement("input");
    sub_input.type = "text";
    sub_input.id = label_id;
    sub_input.value = value;
    sub_input.setAttribute("class", "form-control");

    sub_div.appendChild(sub_input);

}

function post_app(app_id) {
    if (app_id === null) {
        dest_url = "add";
    }else {
        dest_url = "update";
    }

    var app_name = document.getElementById("app_name").value;
    var app_url = document.getElementById("app_url").value;
    var app_remark = document.getElementById("app_remark").value;

    $.ajax({
        type: "post",
        url: "manage/application/" + dest_url,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "applicationId": app_id,
            "applicationName": app_name,
            "url": app_url,
            "remark": app_remark}),
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