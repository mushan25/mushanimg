package com.hzb.ucenter.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hzb.base.exception.MushanImgException;
import com.hzb.ucenter.mapper.MsMenuMapper;
import com.hzb.ucenter.mapper.MsUserMapper;
import com.hzb.ucenter.model.dto.AuthParamsDto;
import com.hzb.ucenter.model.dto.MsUserExt;
import com.hzb.ucenter.model.po.MsMenu;
import com.hzb.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/10
 */
@Slf4j
@Component
public class UserServiceImpl implements UserDetailsService {
    public static final String BEAN_PREFIX = "_authservice";

    private final MsUserMapper msUserMapper;

    private final MsMenuMapper msMenuMapper;

    private final ApplicationContext applicationContext;

    public UserServiceImpl(MsUserMapper msUserMapper, MsMenuMapper msMenuMapper, ApplicationContext applicationContext) {
        this.msUserMapper = msUserMapper;
        this.msMenuMapper = msMenuMapper;
        this.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 获取传入的参数
        AuthParamsDto authParamsDto = null;
        try {
            JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e){
            MushanImgException.cast("请求认证参数不符合要求");
        }

        // 认证类型
        String authType = authParamsDto.getAuthType();
        //根据认证类型从spring容器取出指定的bean
        String beanName = authType + BEAN_PREFIX;
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);
        //调用统一execute方法完成认证
        MsUserExt msUserExt = authService.execute(authParamsDto);
        //封装xcUserExt用户信息为UserDetails
        //根据UserDetails对象生成令牌
        UserDetails userPrincipal = getUserPrincipal(msUserExt);

        return userPrincipal;
    }

    /**
     * 查询用户信息
     * @param msUserExt 用户id，主键
     * @return 用户信息
     */
    public UserDetails getUserPrincipal(MsUserExt msUserExt){
        String password = msUserExt.getPassword();
        // 权限
        String[] authorities = {"test"};
        //根据用户id查询用户的权限
        List<MsMenu> msMenus = msMenuMapper.selectPermissionByUserId(msUserExt.getUserName());
        if (msMenus.size() > 0){
            //拿到了用户拥有的权限标识符
            List<String> permissions = new ArrayList<>();
            msMenus.forEach(m ->{
                permissions.add(m.getPerms());
            });
            // 将permissions转成数组
            authorities = permissions.toArray(new String[0]);
        }

        msUserExt.setPassword(null);
        // 将用户信息转json
        String userJson = JSON.toJSONString(msUserExt);
        UserDetails userDetails = User.withUsername(userJson).password(password).authorities(authorities).build();
        return userDetails;
    }
}
