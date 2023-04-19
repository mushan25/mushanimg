package com.hzb.system.role.gatewayimpl.database;

import com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-04-19 14:20:22
* @Entity com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO
*/
public interface RoleMapper extends BaseMapper<RoleDO> {

    /**
     * 根据userId去中间表查询角色id
     * @param userId 用户id
     * @return 角色id list
     */
    List<Long> selectRoleIdsByUserId(Long userId);

}




