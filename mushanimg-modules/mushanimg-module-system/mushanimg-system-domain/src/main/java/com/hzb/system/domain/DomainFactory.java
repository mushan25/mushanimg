package com.hzb.system.domain;

import com.hzb.system.domain.menu.model.entities.Menu;
import com.hzb.system.domain.role.model.entities.Role;
import com.hzb.system.domain.user.model.entities.User;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
public class DomainFactory {
    public static User getUser(){
        return new User();
    }

    public static Role getRole() {
        return new Role();
    }

    public static Menu getMenu() {
        return new Menu();
    }
}
