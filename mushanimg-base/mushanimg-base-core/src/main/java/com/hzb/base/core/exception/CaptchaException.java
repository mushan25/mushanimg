package com.hzb.base.core.exception;

import java.io.Serial;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public class CaptchaException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 4541152219363919874L;

    public CaptchaException(String msg){
        super(msg);
    }
}
