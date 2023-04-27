package com.hzb.system.domain.user.model.aggregates;

import com.hzb.system.domain.user.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author: hzb
 * @Date: 202   3/4/19
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class AuthUser {
    private User user;
    private Set<String> roleKeys;
    private Set<String> permissions;

}
