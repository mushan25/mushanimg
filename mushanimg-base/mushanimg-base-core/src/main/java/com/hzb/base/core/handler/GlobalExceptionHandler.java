package com.hzb.base.core.handler;

import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ServiceConfigurationError;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceConfigurationError.class)
    public AjaxResult handlerServiceException(ServiceException e){
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return code != null ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }
}
