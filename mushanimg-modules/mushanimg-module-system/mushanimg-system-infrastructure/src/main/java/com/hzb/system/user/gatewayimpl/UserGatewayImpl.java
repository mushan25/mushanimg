package com.hzb.system.user.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.domain.DomainFactory;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.user.gatewayimpl.database.UserMapper;
import com.hzb.system.user.gatewayimpl.database.dataobject.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【ms_user(用户信息表)】的数据库操作Service实现
* @createDate 2023-04-17 16:03:52
*/
@Service
public class UserGatewayImpl extends ServiceImpl<UserMapper, UserDO> implements UserGateway {

    private final UserMapper userMapper;

    public UserGatewayImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getByUserName(String userName) {

        // 1、根据用户名查询用户信息
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUserName, userName));

        // 2、判断是否成功获取
        if (userDO == null){
            return null;
        }

        // 3、获取返回User
        User user = DomainFactory.getUser();
        // 4、拷贝用户
        BeanUtils.copyProperties(userDO, user);
        user.setPassword(userDO.getPassword());

        return user;
    }

    @Override
    public AuthUser getAuthUserByUserName(String userName) {
        return null;
    }
}




