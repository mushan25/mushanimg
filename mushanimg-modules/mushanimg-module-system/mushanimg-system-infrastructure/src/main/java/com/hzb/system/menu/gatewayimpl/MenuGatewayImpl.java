package com.hzb.system.menu.gatewayimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.convertor.MenuConvertor;
import com.hzb.system.domain.menu.gateway.MenuGateway;
import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.menu.gatewayimpl.database.MenuMapper;
import com.hzb.system.menu.gatewayimpl.database.dataobject.MenuDO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【ms_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-04-19 14:09:31
*/
@Service
public class MenuGatewayImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuGateway {

    private final MenuMapper menuMapper;

    public MenuGatewayImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> getMenuByIds(Set<Long> menuIds) {
        // 1、根据menuId list查询menu信息
        List<MenuDO> menuDOS = menuMapper.selectBatchIds(menuIds);

        // 2、判断是否查询成功
        if ( menuDOS == null){
            return null;
        }

        // 4、拷贝菜单信息

        return MenuConvertor.INSTANCT.menuDOList2MenuList(menuDOS);
    }

    @Override
    public Set<Long> getMenuIdsByRoleIds(List<Long> roleIds) {
        return new HashSet<>(menuMapper.selectMenuIdsByRoleIds(roleIds));
    }

    @Override
    public Set<String> getPermissions(List<Menu> menus) {
        return menus.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRoutes(List<Menu> menus) {
        return menus.stream().map(Menu::getPath).collect(Collectors.toSet());
    }
}




