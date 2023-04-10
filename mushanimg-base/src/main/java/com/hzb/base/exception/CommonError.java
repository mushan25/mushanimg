package com.hzb.base.exception;

/**
 * @author: hzb
 * @Date: 2023/3/30
 */
public enum CommonError {
    // 执行过程异常
    UNKOWN_ERROR("执行过程异常,请重试"),
    // 非法参数
    PARAMS_ERROR("非法参数"),
    // 对象为空
    OBJECT_NULL("对象为空"),
    // 查询结构为空
    QUERY_NULL("查询结构为空"),
    // 请求参数为空
    REQUEST_NULL("请求参数为空");

    private final String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    CommonError(String errMessage) {
        this.errMessage = errMessage;
    }
}
