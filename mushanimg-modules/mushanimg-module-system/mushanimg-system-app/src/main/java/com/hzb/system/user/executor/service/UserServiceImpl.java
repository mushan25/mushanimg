package com.hzb.system.user.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.LoginUserInfoCO;
import com.hzb.system.user.dto.clientobject.UserCO;
import com.hzb.system.user.executor.command.query.LoginUserInfoQryExe;
import com.hzb.system.user.executor.command.query.UserListQryExe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserServiceI {

    private final UserListQryExe userListQryExe;
    private final LoginUserInfoQryExe loginUserInfoQryExe;

    @Override
    public PageResponse<UserCO> getUserList(UserListQry userListQry) {
        return userListQryExe.execute(userListQry);
    }

    @Override
    public SingleResponse<LoginUserInfoCO> getUserInfo() {
        return loginUserInfoQryExe.execute();
    }
}
