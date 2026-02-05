package org.example.order.exception;

import org.example.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//全局异常处理器
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){
        return R.error(500,e.getMessage());
    }
}
