package com.hzb.base.core.exception;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public class CaptchaException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg){
        super(msg);
    }
}
