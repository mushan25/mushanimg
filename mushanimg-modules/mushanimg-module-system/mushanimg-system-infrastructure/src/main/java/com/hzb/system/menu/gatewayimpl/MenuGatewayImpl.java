package com.hzb.system.menu.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.domain.menu.gateway.MenuGateway;
import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.menu.gatewayimpl.database.MenuMapper;
import com.hzb.system.menu.gatewayimpl.database.dataobject.MenuDO;
import io.github.zhaord.mapstruct.plus.IObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【ms_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-04-19 14:09:31
*/
@Service
public class MenuGatewayImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuGateway {

    private final MenuMapper menuMapper;

    private final IObjectMapper mapper;

    public MenuGatewayImpl(MenuMapper menuMapper, IObjectMapper mapper) {
        this.menuMapper = menuMapper;
        this.mapper = mapper;
    }

    @Override
    public List<Menu> getMenuByIds(List<Long> menuIds) {
        // 1、判断传入参数是否合法
        if (menuIds == null || menuIds.isEmpty()) {
            return null;
        }
        // 2、根据menuId list查询menu信息
        List<MenuDO> menuDOS = menuMapper.selectBatchIds(menuIds);

        // 3、判断是否查询成功
        if ( menuDOS == null){
            return null;
        }

        // 4、拷贝菜单信息

        return mapper.map(menuDOS, Menu.class);
    }

    @Override
    public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {
        // 1、判断传入参数是否合法
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }
        // 2、查询menuIds
        return menuMapper.selectMenuIdsByRoleIds(roleIds);
    }

    @Override
    public List<String> getPermissions(List<Long> menuIds) {
        // 1、判断传入参数是否合法
        if (menuIds == null || menuIds.isEmpty()){
            return null;
        }

        // 2、查询拥有的权限
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(MenuDO::getPerms).in(MenuDO::getMenuId, menuIds);
        return menuMapper.selectList(wrapper).stream().map(MenuDO::getPerms).collect(Collectors.toList());
    }
}



