package com.hzb.auth.service;

import com.hzb.auth.grpc.UserClient;
import com.hzb.base.core.enums.UserStatus;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;
    private final PasswordService passwordService;

    public UserDetailsServiceImpl(UserClient userClient, PasswordService passwordService) {
        this.userClient = userClient;
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginUser loginUser = userClient.getLoginUser(username);

        if (loginUser == null){
            log.info("登录用户: {} 不存在", username);
            throw new ServiceException("登录用户：" + username + "不存在");
        }
        if (UserStatus.DELETE.getCode().equals(loginUser.getUser().getDelFlag())){
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(loginUser.getUser().getStatus())){
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        passwordService.validate(loginUser);
        return loginUser;
    }
}
