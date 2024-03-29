package com.hzb.base.security.utils;

import com.hzb.base.core.constant.TokenConstants;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.security.form.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@Slf4j
public class SecurityUtils {
    /**
     * 获取用户ID
     */
    public static Long getUserId()
    {
        LoginUser loginUser;
        try {
            loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new RuntimeException("获取用户信息异常");
        }
        return loginUser.getUserId();
    }

    public static LoginUser getUser()
    {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public static AccessMode checkAccessMode() {
        try {
            getUserId();
            return AccessMode.NORM_USER;
        } catch (Exception e) {
            return AccessMode.TOURIST;
        }
    }
}
