package com.hzb.base.security.utils;

import com.hzb.base.core.constant.TokenConstants;
import com.hzb.base.security.form.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request)
    {
        // 从header获取token标识
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token)
    {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }
}
