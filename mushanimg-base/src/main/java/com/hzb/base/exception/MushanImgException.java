package com.hzb.base.exception;

/**
 * @author: hzb
 * @Date: 2023/3/30
 */
public class MushanImgException extends RuntimeException{
    private String errMessage;

    public MushanImgException() {
    }

    public MushanImgException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public static void cast(String message){
        throw new MushanImgException(message);
    }

    public static void cast(CommonError error){
        throw new MushanImgException(error.getErrMessage());
    }
}
