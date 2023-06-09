package com.hzb.system.domain.ability;

import com.hzb.system.domain.menu.gateway.MenuGateway;
import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.domain.role.gateway.RoleGateway;
import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@AllArgsConstructor
@Service
public class DomainService {
    private final UserGateway userGateway;

    private final RoleGateway roleGateway;

    private final MenuGateway menuGateway;

    public AuthUser getAuthUserInfoByName(String userName) {
        Set<String> roleKeySet = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        Set<String> routes = new HashSet<>();
        // 1、获取用户信息
        User user = userGateway.getByUserName(userName);
        if (null != user) {
            // 2、根据用户id获取用户拥有的角色
            List<Role> roleList = roleGateway.getRoleByUserId(user.getUserId());
            roleKeySet = roleList.stream().map(Role::getRoleKey).collect(Collectors.toSet());
            // 3、根据角色id list查询用户拥有的menu id list
            Set<Long> menuIds = menuGateway.getMenuIdsByRoleIds(roleList.stream()
                    .map(Role::getRoleId).collect(Collectors.toList()));
            // 4、根据menuIds查询用户拥有的权限
            List<Menu> menus = menuGateway.getMenuByIds(menuIds);
            permissions = menuGateway.getPermissions(menus);
            routes = menuGateway.getRoutes(menus);
        }
        // 5、返回AuthUser
        return new AuthUser(user, roleKeySet, permissions, routes);
    }


}
