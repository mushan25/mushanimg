package com.hzb.base.exception;

import java.io.Serializable;

/**
 * @author: hzb
 * @Date: 2023/3/30
 */

public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
