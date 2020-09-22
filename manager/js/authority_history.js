

function authority_history() {
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
                alert(message.message);
                clear_cache();
                window.location = login_url;
            }
        }
    });
}