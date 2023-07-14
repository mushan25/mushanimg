package com.hzb.auth.service;

import com.hzb.auth.form.RegisterBody;
import com.hzb.auth.grpc.UserClient;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.SecurityUtils;
import com.hzb.base.security.form.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Component
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;

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

    /**
     * 注册
     */
    public String register(RegisterBody registerBody){
        registerBody.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
        Tuple2<Boolean, String> tuple = userClient.addUser(registerBody);
        if (!tuple.getT1()) {
            throw new ServiceException(tuple.getT2());
        }
        return tuple.getT2();
    }
}
