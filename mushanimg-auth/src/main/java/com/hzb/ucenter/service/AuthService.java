package com.hzb.ucenter.service;

import com.hzb.ucenter.model.dto.AuthParamsDto;
import com.hzb.ucenter.model.dto.MsUserExt;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
public interface AuthService {
    /**
     * 认证方法
     * @param authParamsDto 认证参数
     * @return 用户信息
     */
    MsUserExt execute(AuthParamsDto authParamsDto);
}
