package org.zxy.new_manager.utils;


import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.enums.ResponseEnum;

public class ResponseVOUtil {

    public static ResponseVO success() {
        return new ResponseVO(ResponseEnum.SUCCESS);
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCCESS, data);
    }

    public static ResponseVO error(ResponseEnum responseEnum) {
        return new ResponseVO(responseEnum);
    }

    public static ResponseVO error(Integer code, String message) {
        return new ResponseVO(code, message);
    }
}
