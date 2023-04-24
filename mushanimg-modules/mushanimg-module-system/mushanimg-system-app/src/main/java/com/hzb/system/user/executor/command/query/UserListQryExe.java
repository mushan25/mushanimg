package com.hzb.system.user.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzb.base.core.utils.BeanCopyUtil;
import com.hzb.base.core.web.page.PageParam;
import com.hzb.system.domain.DomainFactory;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.dto.UserGetQry;
import com.hzb.system.user.dto.UserListQry;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple3;

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
        PageParam pageParam = userListQry.getPageParam();
        UserCO userCO = userListQry.getUserCO();
        if (null != userCO){
            BeanUtils.copyProperties(userListQry.getUserCO(), user);
        }
        List<User> userList = userGateway.getUserList(pageParam, user);
        List<UserCO> userCOS = BeanCopyUtil.copyListProperties(userList, UserCO::new);
        Tuple3<Integer, Integer, Integer> pageInfo = user.getPageInfo(userCOS);
        return PageResponse.of(userCOS, pageInfo.getT1(), pageInfo.getT2(), pageInfo.getT3());
    }
}
