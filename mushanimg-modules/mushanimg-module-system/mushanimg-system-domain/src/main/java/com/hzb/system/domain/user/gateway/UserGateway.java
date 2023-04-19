package com.hzb.system.domain.user.gateway;

import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;

/**
* @author Administrator
* @description 针对表【ms_user(用户信息表)】的数据库操作Service
* @createDate 2023-04-17 16:03:52
*/
public interface UserGateway {
    /**
     * 根据用户名查询用户
     * @param userName 用户名称
     * @return 用户
     */
    User getByUserName(String userName);
}
