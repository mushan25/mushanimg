package com.hzb.system.menu.gatewayimpl;

import com.alibaba.cola.exception.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.base.core.utils.BeanCopyUtil;
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

        return BeanCopyUtil.copyListProperties(menuDOS, Menu::new);
    }

    @Override
    public Set<Long> getMenuIdsByRoleIds(List<Long> roleIds) {
        // 1、判断传入参数是否合法
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }
        // 2、查询menuIds
        return new HashSet<>(menuMapper.selectMenuIdsByRoleIds(roleIds));
    }

    @Override
    public Set<String> getPermissions(Set<Long> menuIds) {
        // 1、判断传入参数是否合法
        if (menuIds == null || menuIds.isEmpty()){
            return null;
        }

        // 2、查询拥有的权限
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(MenuDO::getPerms).in(MenuDO::getMenuId, menuIds);
        return menuMapper.selectList(wrapper).stream().map(MenuDO::getPerms).collect(Collectors.toSet());
    }
}




