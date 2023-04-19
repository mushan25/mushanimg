package com.hzb.system.convertor;

import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.menu.gatewayimpl.database.dataobject.MenuDO;
import com.hzb.system.role.gatewayimpl.database.dataobject.RoleDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author hzb
 * @date 2023/4/20 2:17
 */
@Mapper(componentModel = "spring-plus")
public interface RoleMap {
    /**
     * list拷贝
     * @param roleDOs List<RoleDO>
     * @return List<Role>
     */
    List<Role> menuDOs2menus(List<RoleDO> roleDOs);
}
