package com.hzb.system.web.user;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.annotation.Log;
import com.hzb.base.core.annotation.StartPage;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.LoginUserInfoCO;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@RestController
public class UserController {
    private final UserServiceI userService;

    public UserController(UserServiceI userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/list")
    @PreAuthorize("hasAnyAuthority('sys:dashboard:list')")
    @StartPage
    @Log("用户列表")
    public PageResponse<UserCO> list(UserListQry userListQry){
        return userService.getUserList(userListQry);
    }

    @GetMapping(value = "/user/info")
    @PreAuthorize("hasAnyAuthority('sys:dashboard:list')")
    @Log("用户信息")
    public SingleResponse<LoginUserInfoCO> getUserInfo(){
        return userService.getUserInfo();
    }
}
