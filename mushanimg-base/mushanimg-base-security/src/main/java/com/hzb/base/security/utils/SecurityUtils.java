package com.hzb.base.security.utils;

import com.hzb.base.security.form.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
public class SecurityUtils {
    /**
     * 获取用户ID
     */
    public static Long getUserId()
    {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUserId();
    }
}
