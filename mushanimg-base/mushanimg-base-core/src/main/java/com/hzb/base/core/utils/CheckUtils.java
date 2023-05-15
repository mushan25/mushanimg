package com.hzb.base.core.utils;

import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.function.BranchHandler;
import com.hzb.base.core.function.PresentHandler;
import com.hzb.base.core.function.PresentOrElseHandler;
import com.hzb.base.core.function.ThrowException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@Slf4j
public class CheckUtils {
    public static ThrowException isTrue(boolean b){
        return (message) ->{
            if (!b){
                throw new RuntimeException(message);
            }
        };
    }

    public static void isTrue(boolean b, String logMsg, String exceptionMsg){
        if (b){
            log.info(logMsg);
            throw new ServiceException(exceptionMsg);
        }
    }

    public static BranchHandler isTrueOrFalse(boolean b){
        return (trueHandler, falseHandler) -> {
            if (b){
                trueHandler.run();
            }else {
                falseHandler.run();
            }
        };
    }

    public static <T> PresentHandler isPresentRunnable(T t){
        return handler -> {
            if (t != null){
                handler.run();
            }
        };
    }

    public static <T> PresentOrElseHandler<?> isPresent(T t){
        return (action, falseHandler) -> {
            if (t != null){
                action.accept(t);
            }else {
                falseHandler.run();
            }
        };
    }
}
