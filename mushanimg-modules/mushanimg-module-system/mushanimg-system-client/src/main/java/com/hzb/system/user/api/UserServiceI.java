package com.hzb.system.user.api;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.clientobject.UserCO;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
public interface UserServiceI {
    /**
     * 根据用户名查询用户信息
     * @param userGetQry 用户名
     * @return 用户名称
     */
    SingleResponse<UserCO> getUserByUserName(UserGetQry userGetQry);
}
