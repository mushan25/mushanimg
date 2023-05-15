package com.hzb.base.core.function;

import java.util.function.Consumer;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@FunctionalInterface
public interface PresentOrElseHandler<T> {
    /**
     * presentOrElseHandler
     * @param action action
     * @param falseHandler falseHandler
     */
    void presentOrElseHandler(Consumer<? super T> action, Runnable falseHandler);
}
