package com.hzb.system.role.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.domain.DomainFactory;
import com.hzb.system.domain.role.gateway.RoleGateway;
import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.domain.role.model.valueobject.RoleList;
import com.hzb.system.role.gatewayimpl.database.RoleMapper;
import com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO;
import ma.glasnost.orika.MapperFacade;
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
    private final MapperFacade mapperFacade;

    public RoleGatewayImpl(RoleMapper roleMapper, MapperFacade mapperFacade) {
        this.roleMapper = roleMapper;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public List<Role> getRoleByUserId(Long userId) {

        // 1、根据userId获取RoleList信息
        List<RoleDO> roleDOS = roleMapper.selectBatchIds(getRoleIdsByUserId(userId));
        // 2、判断是否查询成功
        if (roleDOS == null){
            return null;
        }
        // 3、拷贝角色信息
        return mapperFacade.mapAsList(roleDOS, Role.class);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return roleMapper.selectRoleIdsByUserId(userId);
    }
}




