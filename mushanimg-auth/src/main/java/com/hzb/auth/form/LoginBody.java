package com.hzb.auth.form;

import lombok.Data;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
