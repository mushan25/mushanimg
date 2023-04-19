package com.hzb.system.domain.ability;

import com.hzb.system.domain.menu.gateway.MenuGateway;
import com.hzb.system.domain.role.gateway.RoleGateway;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Service
public class DomainService {
    private final UserGateway userGateway;

    private final RoleGateway roleGateway;

    private final MenuGateway menuGateway;

    public DomainService(UserGateway userGateway, RoleGateway roleGateway, MenuGateway menuGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
        this.menuGateway = menuGateway;
    }

    public AuthUser getAuthUserInfoByName(String userName) {
        // 1、获取用户信息
        User user = userGateway.getByUserName(userName);
        // 2、根据用户id获取用户拥有的角色
        List<Long> roleIds = roleGateway.getRoleIdsByUserId(user.getUserId());
        // 3、根据角色id list查询用户拥有的menu id list
        List<Long> menuIds = menuGateway.getMenuIdsByRoleIds(roleIds);
        // 4、根据menuIds查询用户拥有的权限
        List<String> permissions = menuGateway.getPermissions(menuIds);
        // 5、返回AuthUser
        return new AuthUser(user, permissions);
    }


}
