package com.hzb.system.convertor;

import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.menu.gatewayimpl.database.dataobject.MenuDO;
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
public interface MenuConvertor {
    MenuConvertor INSTANCT = Mappers.getMapper(MenuConvertor.class);

    /**
     * DO2Menu
     * @param menuDO MenuDO
     * @return Menu
     */
    Menu DO2Menu(MenuDO menuDO);

    /**
     * menu2DO
     * @param menu Menu
     * @return MenuDO
     */
    MenuDO menu2DO(Menu menu);

    /**
     * menuDOList2MenuList
     * @param menuDOS List<MenuDO>
     * @return List<Menu>
     */
    List<Menu> menuDOList2MenuList(List<MenuDO> menuDOS);
}
