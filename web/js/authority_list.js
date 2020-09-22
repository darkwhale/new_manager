

function authority_list() {
    var legal_user_app_list = get_user_app_list();

    if (legal_user_app_list.length === 0) {

        var prompt_info = document.createElement("blockquote");

        var prompt_info_p = document.createElement("p");

        var user_name = getCookie("user_name");
        if (user_name === "") {
            prompt_info_p.innerText = "您暂时未登录";
        }else {
            prompt_info_p.innerText = "您还没有使用权限，快去申请吧～";
        }

        var prompt_info_small = document.createElement("small");
        prompt_info_small.innerText = "一位善意的智者";

        prompt_info.appendChild(prompt_info_p);
        prompt_info.appendChild(prompt_info_small);

        main_body.appendChild(prompt_info);
    }
}