package com.hzb.base.security.form;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@Data
public class UserInfo {
    private Long userId;

    private String userName;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String sex;

    private String avatar;

    private Password password;

    private String status;

    private String delFlag;

    private String loginIp;

    private LocalDate loginDate;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
