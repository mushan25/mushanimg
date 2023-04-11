package com.hzb.auth.controller;

import com.hzb.ucenter.mapper.MsUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
@Slf4j
@RestController
public class LoginController {
    private final MsUserMapper userMapper;

    public LoginController(MsUserMapper userMapper) {
        this.userMapper = userMapper;
    }


}
