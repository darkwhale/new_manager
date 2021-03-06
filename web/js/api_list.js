
function api_list() {

    var app_id = GetRequest()["app_id"];

    $.ajax({
        type: "get",
        url: "manage/application/findByApp",
        contentType: "application/json;charset=utf-8",
        data: {"applicationId": app_id},
        dataType: "json",
        success: function (message) {
            if (message && message.code === 0) {
                var main_body = document.getElementById("main_container");
                main_body.innerHTML = "";

                var top_body = document.createElement("div");
                main_body.appendChild(top_body);

                add_label(top_body, "应用名： " + message.data.applicationName);
                add_label(top_body, "应用网址： " + message.data.url);
                add_label(top_body, "备注： " + message.data.remark);

                var bottom_body = document.createElement("div");
                main_body.appendChild(bottom_body);

                for (let index in message.data.apiList) {
                    info = message.data.apiList[index];
                    create_element(bottom_body, info);

                }
                if (message.data.apiList.length === 0) {
                    var hr = document.createElement("hr");
                    main_body.appendChild(hr);

                    bottom_body.innerHTML = "";
                    var prompt_info = document.createElement("blockquote");

                    var prompt_info_p = document.createElement("p");
                    prompt_info_p.innerText = "暂时还没有任何api接口";

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

function add_label(container, input_str) {
    var sub_p = document.createElement("p");
    sub_p.innerText = input_str;
    sub_p.setAttribute("style", "font-size: 15px");

    container.appendChild(sub_p);
}


function create_element(container, info) {
    var hr = document.createElement("hr");
    container.appendChild(hr);

    create_sub_element(container, "api: ", info.apiValue);
    create_sub_element(container, "api名: ", info.apiName);
    create_sub_element(container, "api说明: \n", info.remark);

}


function create_sub_element(container, sub_label, sub_value) {
    var sub_api_name = document.createElement("div");
    var sub_api_name_label = document.createElement("strong");
    sub_api_name.appendChild(sub_api_name_label);

    sub_api_name_label.innerText = sub_label;

    var sub_api_name_info = document.createElement("span");
    sub_api_name_info.innerText = sub_value;
    sub_api_name.appendChild(sub_api_name_info);
    container.appendChild(sub_api_name);

}

