package com.hzb.base.core.enums;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
public enum UserStatus {
    /**
     * 用户状态枚举
     */
    OK("0", "正常"),
    DISABLE("1", "停用"),
    DELETE("2", "删除");

    private final String code;
    private final String info;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
