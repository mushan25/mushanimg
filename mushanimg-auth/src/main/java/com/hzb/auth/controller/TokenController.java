package com.hzb.auth.controller;

import com.hzb.auth.form.LoginBody;
import com.hzb.auth.form.LoginUser;
import com.hzb.auth.form.RegisterBody;
import com.hzb.auth.service.LoginService;
import com.hzb.auth.service.TokenService;
import com.hzb.base.core.domain.ResultBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@RestController
public class TokenController {

    private final LoginService loginService;
    private final TokenService tokenService;

    public TokenController(LoginService loginService, TokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResultBody<?> login(@RequestBody @Validated LoginBody loginBody){
        LoginUser userInfo = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return ResultBody.ok(tokenService.createToken(userInfo));
    }

    @PostMapping("/refresh")
    public ResultBody<?> refresh(HttpServletRequest request){
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null){
            tokenService.refreshToken(loginUser);
            return ResultBody.ok();
        }
        return ResultBody.ok();
    }

    @PostMapping("/register")
    public ResultBody<?> register(@RequestBody @Validated RegisterBody registerBody){
        String msg = loginService.register(registerBody.getUsername(), registerBody.getPassword());
        return ResultBody.ok(null, msg);
    }

}
