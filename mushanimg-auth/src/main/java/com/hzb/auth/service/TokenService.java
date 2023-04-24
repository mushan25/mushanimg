package com.hzb.auth.service;

import com.hzb.auth.form.LoginUser;
import com.hzb.base.core.constant.CacheConstants;
import com.hzb.base.core.constant.SecurityConstants;
import com.hzb.base.core.constant.TokenConstants;
import com.hzb.base.core.utils.JwtUtils;
import com.hzb.base.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Component
public class TokenService {
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;
    private final static long expireTime = CacheConstants.EXPIRATION;
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final RedisService redisService;

    public TokenService(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 创建令牌
     * @param loginUser 登录用户信息
     * @return 令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser){
        String token = UUID.randomUUID().toString();
        Long userId = loginUser.getUserId();
        String userName = loginUser.getUser().getUserName();
        loginUser.setToken(token);
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>(3);
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        //接口返回信息
        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request){
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        LoginUser user = null;
        try {
            if (StringUtils.isNotEmpty(token)){
                String userKey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(userKey);
                return user;
            }
        }catch (Exception e){

        }
        return null;
    }

    public void verifyToken(LoginUser loginUser){
        long expireTime1 = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime1 - currentTime <= MILLIS_MINUTE_TEN){

        }
    }

    /**
     * 刷新令牌有效期
     * @param loginUser
     */
    public void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }
    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}
