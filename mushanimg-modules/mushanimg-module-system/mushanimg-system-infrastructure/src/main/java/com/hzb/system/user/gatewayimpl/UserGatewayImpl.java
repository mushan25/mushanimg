package com.hzb.system.user.gatewayimpl;

import com.alibaba.cola.exception.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.convertor.UserConvertor;
import com.hzb.system.domain.DomainFactory;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.gatewayimpl.database.UserMapper;
import com.hzb.system.user.gatewayimpl.database.dataobject.UserDO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_user(用户信息表)】的数据库操作Service实现
* @createDate 2023-04-17 16:03:52
*/
@AllArgsConstructor
@Service
public class UserGatewayImpl extends ServiceImpl<UserMapper, UserDO> implements UserGateway {

    private final UserMapper userMapper;


    @Override
    public List<User> getUserList(User user) {
        UserDO userDO = UserConvertor.INSTANCT.user2DO(user);
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != userDO.getUserId() && 0 != userDO.getUserId(), UserDO::getUserId, userDO.getUserId())
                .eq(StringUtils.isNotEmpty(userDO.getUserName()), UserDO::getUserName, userDO.getUserName())
                .eq(StringUtils.isNotEmpty(userDO.getStatus()), UserDO::getStatus, userDO.getStatus());
        List<UserDO> userDOS = userMapper.selectList(wrapper);

        return UserConvertor.INSTANCT.userDOList2UserList(userDOS);
    }

    @Override
    public User getByUserName(String userName) {

        // 1、根据用户名查询用户信息
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUserName, userName));

        // 2、判断是否成功获取
        if (userDO == null){
            throw new BizException("用户不存在");
        }

        // 3、获取返回User
        User user = DomainFactory.getUser();
        // 4、拷贝用户
        UserConvertor.INSTANCT.DO2User(user, userDO);
        user.setPassword(userDO.getPassword());

        return user;
    }

    @Override
    public Long registerUser(User user) {
        user.initNickName();
        UserDO userDO = UserConvertor.INSTANCT.user2DO(user);
        userMapper.insert(userDO);
        return userDO.getUserId();


    }

    @Override
    public boolean checkUserNameUnique(User user) {
        if (null == user){
            return false;
        }
        return userMapper.exists(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getUserName, user.getUserName()));
    }

    @Override
    public User getUploadUserInfoById(Long userId) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserDO::getUserName, UserDO::getUploadSize)
                .eq(UserDO::getUserId, userId);
        return UserConvertor.INSTANCT.DO2User(userMapper.selectOne(wrapper));
    }
}




