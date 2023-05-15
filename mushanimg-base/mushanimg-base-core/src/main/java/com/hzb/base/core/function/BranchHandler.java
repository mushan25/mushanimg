package com.hzb.base.core.function;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@FunctionalInterface
public interface BranchHandler {
    /**
     * trueOrFalseHandler
     * @param trueHandler true时执行
     * @param falseHandler false时执行
     */
    void trueOrFalseHandler(Runnable trueHandler, Runnable falseHandler);
}
