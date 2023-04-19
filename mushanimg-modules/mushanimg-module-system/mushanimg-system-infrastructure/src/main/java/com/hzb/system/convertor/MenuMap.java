package com.hzb.system.convertor;

import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.menu.gatewayimpl.database.dataobject.MenuDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author hzb
 * @date 2023/4/20 2:15
 */
@Mapper(componentModel = "spring-plus")
public interface MenuMap {

    /**
     * list拷贝
     * @param menuDOs List<MenuDO>
     * @return List<Menu>
     */
    List<Menu> menuDOs2menus(List<MenuDO> menuDOs);
}
