package com.hzb.system.web.user;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.github.pagehelper.PageHelper;
import com.hzb.base.core.annotation.StartPage;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@RestController("/user")
public class UserController {
    private final UserServiceI userService;

    public UserController(UserServiceI userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/list")
    @StartPage
    public PageResponse<UserCO> list(UserListQry userListQry){
        return userService.getUserList(userListQry);
    }
}
