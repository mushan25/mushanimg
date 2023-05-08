package com.hzb.base.security.handler;

import com.hzb.base.core.constant.HttpStatus;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: hzb
 * @Date: 2023/4/27
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handlerAccessDeniedException(AccessDeniedException e){
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有访问权限，请联系管理员授权" + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handlerServiceException(ServiceException e){
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return code != null ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }
}
