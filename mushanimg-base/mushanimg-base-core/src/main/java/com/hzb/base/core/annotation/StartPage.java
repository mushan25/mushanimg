package com.hzb.base.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hzb
 * @Date: 2023/4/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface StartPage {
    int pageNum() default 1;
    int pageSize() default 10;

    String isAsc() default "asc";
}
