package com.hzb.auth.controller;

import com.hzb.auth.form.LoginBody;
import com.hzb.auth.form.RegisterBody;
import com.hzb.auth.service.LoginService;
import com.hzb.base.core.annotation.Log;
import com.hzb.base.core.domain.ResultBody;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.service.TokenService;
import com.hzb.base.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
    @Log("登录")
    public ResultBody<?> login(@RequestBody @Validated LoginBody loginBody){
        LoginUser userInfo = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return ResultBody.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("/logout")
    @Log("登出")
    public ResultBody<?> logout(HttpServletRequest request){
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)){
            tokenService.delLoginUser(token);
        }
        return ResultBody.ok();
    }

    @PostMapping("/refresh")
    @Log("刷新token")
    public ResultBody<?> refresh(HttpServletRequest request){
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null){
            tokenService.refreshToken(loginUser);
            return ResultBody.ok();
        }
        return ResultBody.ok();
    }

    @PostMapping("/register")
    @Log("注册")
    public ResultBody<?> register(@RequestBody @Validated RegisterBody registerBody){
        String msg = loginService.register(registerBody);
        return ResultBody.ok(null, msg);
    }

}
