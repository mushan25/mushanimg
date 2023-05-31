package com.hzb.system.role.gatewayimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.convertor.RoleConvertor;
import com.hzb.system.domain.role.gateway.RoleGateway;
import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.role.gatewayimpl.database.RoleMapper;
import com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【ms_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-04-19 14:20:22
 */
@Service
public class RoleGatewayImpl extends ServiceImpl<RoleMapper, RoleDO>
        implements RoleGateway {

    private final RoleMapper roleMapper;

    public RoleGatewayImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> getRoleByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        // 1、根据userId获取RoleList信息
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (null != roleIds && !roleIds.isEmpty()) {
            List<RoleDO> roleDOS = roleMapper.selectBatchIds(roleIds);
            return RoleConvertor.INSTANCT.roleDOList2RoleList(roleDOS);
        }
        return null;
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return roleMapper.selectRoleIdsByUserId(userId);
    }
}




