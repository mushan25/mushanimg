package com.hzb.auth.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 2, max = 10, message = "用户名长度必须为2-10位")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 5, max = 16, message = "密码长度必须为5-16位")
    private String password;
}
