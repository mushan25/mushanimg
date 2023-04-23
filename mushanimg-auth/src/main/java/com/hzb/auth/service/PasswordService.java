package com.hzb.auth.service;

import com.hzb.auth.form.LoginUser;
import com.hzb.base.core.constant.CacheConstants;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
        Authentication authentication = new ThreadLocal<Authentication>().get();
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Integer retryCount = redisService.getCacheObject(getCacheKey(username));
        if (retryCount == null){
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount){
            throw new ServiceException("密码输入错误" + maxRetryCount + "次，帐户锁定" + lockTime + "分钟");
        }
        if (matches(loginUser, password)){
            retryCount = retryCount + 1;
            throw new ServiceException("密码错误");
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword, loginUser.getPassword());
    }
}
