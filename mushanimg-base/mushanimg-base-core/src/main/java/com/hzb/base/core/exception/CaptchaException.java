package com.hzb.base.core.exception;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public class CaptchaException extends RuntimeException{

    private static final long serialVersionUID = 4541152219363919874L;

    public CaptchaException(String msg){
        super(msg);
    }
}
