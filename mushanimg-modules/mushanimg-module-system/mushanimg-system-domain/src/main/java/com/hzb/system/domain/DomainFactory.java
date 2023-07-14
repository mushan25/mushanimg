package com.hzb.system.domain;

import com.hzb.system.domain.user.model.entities.User;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
public class DomainFactory {
    public static User getUser(){
        return new User();
    }
}
