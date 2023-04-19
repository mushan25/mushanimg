package com.hzb.system.menu.getawayimpl.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.system.menu.getawayimpl.database.dataobject.MenuDO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-04-19 14:09:31
* @Entity com.hzb.system.user.do.MenuDO
*/
public interface MenuMapper extends BaseMapper<MenuDO> {

    /**
     * 根据角色ids查询菜单ids
     * @param roleIds 角色id list
     * @return 菜单id list
     */
    List<Long> selectMenuIdsByRoleIds(List<Long> roleIds);
}




