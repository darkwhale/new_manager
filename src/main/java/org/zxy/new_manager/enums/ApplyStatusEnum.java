package org.zxy.new_manager.enums;

import lombok.Getter;

@Getter
public enum ApplyStatusEnum {

    WAIT(0, "待处理"),

    OK(1, "已批准"),

    DENIED(2, "已拒绝"),

    ;

    private Integer code;

    private String message;

    ApplyStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
