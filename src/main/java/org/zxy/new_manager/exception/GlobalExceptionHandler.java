package org.zxy.new_manager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.zxy.new_manager.VO.ResponseVO;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.utils.ResponseVOUtil;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO validHandler(MethodArgumentNotValidException e) {
        log.error("输入参数错误 {}", e.getBindingResult());
        return ResponseVOUtil.error(ResponseEnum.INPUT_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseVO fleaExceptionHandler(ApiException e) {
        return ResponseVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseVO httpClientErrorHandler(HttpClientErrorException e) {
        return ResponseVOUtil.error(e.getStatusCode().value(), e.getMessage());
    }

}
