package com.hzb.system.user.executor.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Component
public class UserGetQryExe {
    private final UserGateway userGateway;

    public UserGetQryExe(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public SingleResponse<UserCO> execute(UserGetQry userGetByNameQry){
        User userInfo = userGateway.getByUserName(userGetByNameQry.getUserName());
        UserCO userCO = new UserCO();
        BeanUtils.copyProperties(userInfo, userCO);
        return SingleResponse.of(userCO);
    }
}
