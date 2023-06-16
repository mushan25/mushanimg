package com.hzb.base.core.annotation;

import com.hzb.base.core.enums.AccessMode;

import java.lang.annotation.*;

/**
 * @author: hzb
 * @Date: 2023/6/14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AccessModeAnnotation {
    AccessMode taxType();
}
