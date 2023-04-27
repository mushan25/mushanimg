package com.hzb.base.security.service;

import com.hzb.base.core.constant.CacheConstants;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.SecurityUtils;
import com.hzb.base.redis.service.RedisService;
import com.hzb.base.security.form.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Component
public class PasswordService {

    private int maxRetryCount = CacheConstants.PASSWORD_MAX_RETRY_COUNT;

    private Long lockTime = CacheConstants.PASSWORD_LOCK_TIME;

    private final RedisService redisService;

    public PasswordService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void validate(LoginUser loginUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Integer retryCount = redisService.getCacheObject(getCacheKey(username));
        if (retryCount == null){
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount){
            String errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟", maxRetryCount, lockTime);
            throw new ServiceException(errMsg);
        }
        if (!matches(loginUser, password)){
            retryCount = retryCount + 1;
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new ServiceException("用户不存在/密码错误");
        }
    }

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    /**
     * 校验密码
     * @param loginUser 查询出来数据库用户数据
     * @param rawPassword 输入的数据
     * @return 校验结果
     */
    public boolean matches(LoginUser loginUser, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, loginUser.getPassword());
    }
}
