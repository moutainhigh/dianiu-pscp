package com.edianniu.pscp.das.common.exception;

import org.springframework.web.bind.annotation.*;

/**
 * result统一异常处理
 *
 * @author tandingbo
 * @create 2017-11-06 15:06
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus
    public RestExceptionResult runtimeExceptionHandler(Exception e) {
        RestExceptionResult result = RestExceptionResult.newInstance();
        result.set(500, e.getMessage());
        return result;
    }
}
