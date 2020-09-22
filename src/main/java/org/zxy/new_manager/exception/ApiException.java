package org.zxy.new_manager.exception;

import lombok.Getter;
import org.zxy.new_manager.enums.ResponseEnum;

@Getter
public class ApiException extends RuntimeException {

    private Integer code;

    private String message;

    public ApiException(ResponseEnum responseEnum) {
//        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
}
