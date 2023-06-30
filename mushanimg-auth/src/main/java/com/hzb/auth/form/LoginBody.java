package com.hzb.auth.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{2,10}$", message = "用户名必须为字母开头，后面可以是字母或数字，长度为2-10位")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 5, max = 16, message = "密码长度必须为5-16位")
    private String password;
}
