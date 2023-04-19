package com.hzb.system.domain.user.model.aggregates;

import com.hzb.system.domain.menu.model.valueobject.Permissions;
import com.hzb.system.domain.user.model.entities.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Data
@Accessors(chain = true)
public class AuthUser {
    private User user;

    private Permissions permissions;

    public AuthUser(User user, List<String> permissions){
        this.user = user;
        this.permissions.setPermissions(permissions);
    }

}
