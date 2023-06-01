package com.hzb.base.core.annotation;

import java.lang.annotation.*;

/**
 * @author hzb
 * @date 2023/6/1 22:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Log {
    /**
     * 操作描述
     * @return 操作描述
     */
    String value() default "";
}
