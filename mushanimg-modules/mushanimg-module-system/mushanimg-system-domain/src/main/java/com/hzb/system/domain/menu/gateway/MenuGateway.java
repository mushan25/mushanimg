package com.hzb.system.domain.menu.gateway;


import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.domain.menu.model.valueobject.MenuList;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-04-19 14:09:31
*/
public interface MenuGateway {
    /**
     * 根据角色id list获取menu信息
     * @param menuIds 菜单id list
     * @return menuList
     */
    List<Menu>  getMenuByIds(List<Long> menuIds);

    /**
     * 根据角色id list获取menuIds
     * @param roleIds 角色id list
     * @return menuId list
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 根据角色id list获取权限字符
     * @param menuIds 菜单id list
     * @return permissions
     */
    List<String> getPermissions(List<Long> menuIds);
}
