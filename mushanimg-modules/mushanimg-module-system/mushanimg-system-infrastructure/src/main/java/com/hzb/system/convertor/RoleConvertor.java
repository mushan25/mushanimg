package com.hzb.system.convertor;

import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
        , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleConvertor {
    RoleConvertor INSTANCT = Mappers.getMapper(RoleConvertor.class);

    /**
     * DO2Role
     * @param roleDO RoleDO
     * @return Role
     */
    Role DO2Role(RoleDO roleDO);

    /**
     * role2DO
     * @param role Role
     * @return RoleDO
     */
    RoleDO role2DO(Role role);

    /**
     * roleDOList2RoleList
     * @param roleDOS List<RoleDO>
     * @return List<Role>
     */
    List<Role> roleDOList2RoleList(List<RoleDO> roleDOS);
}
