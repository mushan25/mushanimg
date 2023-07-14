package com.hzb.base.core.exception;

import java.io.Serial;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
public final class ServiceException extends RuntimeException
{

    @Serial
    private static final long serialVersionUID = -6642644055472065251L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}
