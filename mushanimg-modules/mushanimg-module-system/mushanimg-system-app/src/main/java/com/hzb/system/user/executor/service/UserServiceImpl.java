package com.hzb.system.user.executor.service;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.user.api.UserServiceI;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import com.hzb.system.user.executor.command.query.UserGetQryExe;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Service
public class UserServiceImpl implements UserServiceI {

    private final UserGetQryExe userGetQryExe;

    public UserServiceImpl(UserGetQryExe userGetQryExe) {
        this.userGetQryExe = userGetQryExe;
    }

    @Override
    public SingleResponse<UserCO> getUserByUserName(UserGetQry userGetQry) {
        return userGetQryExe.execute(userGetQry);
    }
}
