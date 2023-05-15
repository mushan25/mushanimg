package com.hzb.base.core.function;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@FunctionalInterface
public interface ThrowException {
    /**
     * 抛出异常
     * @param message 消息
     */
    void throwMessage(String message);
}
