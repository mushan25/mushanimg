package com.hzb.system.domain.user.gateway;

import com.hzb.system.domain.user.model.entities.User;

import java.util.List;

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

    /**
     * 获取用户列表
     * @param user 用户信息
     * @return 用户列表
     */
    List<User> getUserList(User user);

    /**
     * 注册用户
     * @param user 用户信息
     * @return 注册结果
     */
    Long registerUser(User user);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkUserNameUnique(User user);

    /**
     * 获取用户信息
     * @param userId 登录用户id
     * @return 登录用户信息
     */
    User getUploadUserInfoById(Long userId);
}
