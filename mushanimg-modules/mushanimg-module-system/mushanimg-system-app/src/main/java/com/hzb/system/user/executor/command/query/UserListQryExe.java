package com.hzb.system.user.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.domain.CustomPageInfo;
import com.hzb.base.core.utils.BeanCopyUtil;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.system.domain.DomainFactory;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Component
public class UserListQryExe {
    private final UserGateway userGateway;

    public UserListQryExe(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public PageResponse<UserCO> execute(UserListQry userListQry){
        User user = DomainFactory.getUser();
        UserCO userCO = userListQry.getUserCO();
        if (null != userCO){
            BeanUtils.copyProperties(userListQry.getUserCO(), user);
        }
        List<User> userList = userGateway.getUserList(user);
        List<UserCO> userCOS = BeanCopyUtil.copyListProperties(userList, UserCO::new);
        CustomPageInfo pageInfo = PageUtils.getPageInfo(userCOS);
        return PageResponse.of(userCOS, pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
    }
}
