package org.zxy.new_manager.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(0, "成功"),

    MANAGER_ERROR(1, "管理员账号或密码错误"),

    MANAGER_NOT_LOGIN(2, "管理员未登录"),

    INPUT_ERROR(5, "输入参数有误"),

    USER_EXIST(10, "用户已存在"),

    USER_NOT_EXIST(11, "用户不存在"),

    USER_OR_PASSWORD_ERROR(12, "用户名或密码错误"),

    USER_NOT_LOGIN(13, "用户未登录"),

    APPLICATION_EXIST(20, "应用已存在"),

    APPLICATION_NOT_EXIST(21, "应用不存在"),

    API_EXIST(30, "api已存在"),

    API_NOT_EXIST(31, "api不存在"),

    AUTHORITY_EXIST(40, "权限已存在"),

    AUTHORITY_NOT_EXIST(41, "权限不存在"),

    AUTHORITY_ERROR(42, "权限不足"),

    URL_PATH_ERROR(50, "请求url错误"),

    API_NOT_PRESENT(51, "用户api未输入"),

    APPLY_EXIST(60, "用户已申请，正在审批中"),

    APPLY_NOT_EXIST(61, "审批数据不存在"),

    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
