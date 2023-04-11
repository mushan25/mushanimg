package com.hzb.users.service.impl;

import com.hzb.users.model.po.MsUser;
import com.hzb.users.mapper.MsUserMapper;
import com.hzb.users.service.MsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author hzb
 * @since 2023-04-11
 */
@Service
public class MsUserServiceImpl extends ServiceImpl<MsUserMapper, MsUser> implements MsUserService {

}
