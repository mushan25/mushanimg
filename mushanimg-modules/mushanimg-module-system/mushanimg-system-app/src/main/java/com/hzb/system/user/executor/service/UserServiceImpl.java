package com.hzb.system.user.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import com.hzb.system.user.executor.command.query.UserListQryExe;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Service
public class UserServiceImpl implements UserServiceI {

    private final UserListQryExe userListQryExe;

    public UserServiceImpl(UserListQryExe userListQryExe) {
        this.userListQryExe = userListQryExe;
    }


    @Override
    public PageResponse<UserCO> getUserList(UserListQry userListQry) {
        return userListQryExe.execute(userListQry);
    }
}
