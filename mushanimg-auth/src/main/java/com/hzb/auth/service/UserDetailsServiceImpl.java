package com.hzb.auth.service;

import com.hzb.auth.grpc.UserClient;
import com.hzb.base.core.enums.UserStatus;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.CheckUtils;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.service.PasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;
    private final PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginUser loginUser;
        try {
            loginUser = userClient.getLoginUser(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CheckUtils.isTrue(Objects.isNull(loginUser),
                String.format("登录用户: %s 不存在", username),
                String.format("登录用户: %s 不存在", username));

        CheckUtils.isTrue(UserStatus.DELETE.getCode().equals(loginUser.getUser().getDelFlag()),
                String.format("登录用户: %s 已被删除", username),
                String.format("对不起，您的账号: %s 已被删除", username));

        CheckUtils.isTrue(UserStatus.DISABLE.getCode().equals(loginUser.getUser().getStatus()),
                String.format("登录用户: %s 已被停用", username),
                String.format("对不起，您的账号: %s 已停用", username));

        passwordService.validate(loginUser);
        return loginUser;
    }
}
