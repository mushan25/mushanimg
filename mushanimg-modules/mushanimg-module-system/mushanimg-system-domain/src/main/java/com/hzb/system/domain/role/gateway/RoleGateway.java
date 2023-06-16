package com.hzb.system.domain.role.gateway;

import com.hzb.system.domain.role.model.entities.Role;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【ms_role(角色信息表)】的数据库操作Service
 * @createDate 2023-04-19 14:20:22
 */
public interface RoleGateway {

    /**
     * 根据用户id获取Role信息
     *
     * @param userId 用户id
     * @return 角色list
     */
    List<Role> getRoleByUserId(Long userId);

    /**
     * 根据用户id获取角色id list
     *
     * @param userId 用户id
     * @return 角色id list
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 用户添加角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 角色信息
     */
    boolean userAddRole(Long userId, Long roleId);
}
