package com.hzb.auth.service;

import com.hzb.auth.form.LoginUser;
import com.hzb.base.core.constant.SecurityConstants;
import com.hzb.base.core.exception.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Component
public class LoginService {

    private final AuthenticationManager authenticationManager;

    public LoginService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public LoginUser login(String username, String password){

        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e){
            if (e instanceof BadCredentialsException){
                throw new ServiceException("密码错误");
            }else {
                throw new ServiceException(e.getMessage());
            }
        }

        return (LoginUser) authentication.getPrincipal();
    }
}
