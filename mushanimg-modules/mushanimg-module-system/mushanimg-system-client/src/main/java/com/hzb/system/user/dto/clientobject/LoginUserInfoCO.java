package com.hzb.system.user.dto.clientobject;

import lombok.Data;

import java.util.Set;

/**
 * @author hzb
 * @date 2023/5/30 21:34
 */
@Data
public class LoginUserInfoCO {
    private UserCO user;
    private Set<String> routes;
}
