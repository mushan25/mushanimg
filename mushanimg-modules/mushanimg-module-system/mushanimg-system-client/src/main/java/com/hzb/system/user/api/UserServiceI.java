package com.hzb.system.user.api;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.LoginUserInfoCO;
import com.hzb.system.user.dto.clientobject.UserCO;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
public interface UserServiceI {
    /**
     * 根据用户名查询用户信息
     * @param userListQry 查询用户参数
     * @return 用户名称
     */
    PageResponse<UserCO> getUserList(UserListQry userListQry);

    /**
     * 获取用户信息
     * @return 用户信息
     */
    SingleResponse<LoginUserInfoCO> getUserInfo();

}
