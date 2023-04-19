package com.hzb.system.web.user;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = "/user")
    public SingleResponse<UserCO> getUserByName(@RequestParam String username){
        UserGetQry userGetQry = new UserGetQry();
        userGetQry.setUserName(username);
        return userService.getUserByUserName(userGetQry);
    }

}
