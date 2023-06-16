package com.hzb.system.user.executor.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.security.form.LoginUser;
import com.hzb.system.convertor.AppUserConvertor;
import com.hzb.system.user.dto.clientobject.LoginUserInfoCO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author hzb
 * @date 2023/5/30 21:40
 */
@Component
public class LoginUserInfoQryExe {
    public SingleResponse<LoginUserInfoCO> execute() {
        return SingleResponse.of(AppUserConvertor
                .INSTANCT.loginUser2LoginUserInfoCO((LoginUser) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal()));
    }
}
