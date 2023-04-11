package com.hzb.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzb.base.exception.MushanImgException;
import com.hzb.ucenter.mapper.MsUserMapper;
import com.hzb.ucenter.model.dto.AuthParamsDto;
import com.hzb.ucenter.model.dto.MsUserExt;
import com.hzb.ucenter.model.po.MsUser;
import com.hzb.ucenter.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {

    private final MsUserMapper msUserMapper;

    private final PasswordEncoder passwordEncoder;

    public PasswordAuthServiceImpl(MsUserMapper msUserMapper, PasswordEncoder passwordEncoder) {
        this.msUserMapper = msUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MsUserExt execute(AuthParamsDto authParamsDto) {

        // 账号
        String username = authParamsDto.getUsername();

        // 账号是否存在
        //根据username账号查询数据库
        MsUser msUser = msUserMapper.selectOne(new LambdaQueryWrapper<MsUser>().eq(MsUser::getUserName, username));
        //查询到用户不存在，要返回null即可，spring security框架抛出异常用户不存在
        if (msUser == null){
            MushanImgException.cast("账号不存在");
        }
        // 验证密码是否正确
        // 如果查到了用户拿到正确的密码
        String passwordDb = msUser.getPassword();
        // 拿到用户输入的密码
        String passwordForm = authParamsDto.getPassword();
        // 校验密码
        boolean matches = passwordEncoder.matches(passwordForm, passwordDb);
        if (!matches){
            MushanImgException.cast("账号或密码错误");
        }
        MsUserExt msUserExt = new MsUserExt();
        BeanUtils.copyProperties(msUser, msUserExt);

        return msUserExt;
    }
}
