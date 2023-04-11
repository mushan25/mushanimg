package com.hzb.ucenter.model.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
@Data
@ToString
@Accessors(chain = true)
public class AuthParamsDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 验证码
     */
    private String checkcode;

    /**
     * 验证码key
     */
    private String checkcodekey;

    /**
     * 认证类型
     */
    private String authType;

    /**
     * 附加数据，作为扩展，不同认证类型可拥有不同的附加数据。
     * 如认证类型为短信时包含smsKey : sms:3d21042d054548b08477142bbca95cfa;
     * 所有情况下都包含clientId
     */
    private Map<String, Object> payload = new HashMap<>();

}
