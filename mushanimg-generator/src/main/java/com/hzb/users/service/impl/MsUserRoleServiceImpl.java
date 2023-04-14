package com.hzb.users.service.impl;

import com.hzb.users.model.po.MsUserRole;
import com.hzb.users.mapper.MsUserRoleMapper;
import com.hzb.users.service.MsUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author hzb
 * @since 2023-04-14
 */
@Service
public class MsUserRoleServiceImpl extends ServiceImpl<MsUserRoleMapper, MsUserRole> implements MsUserRoleService {

}
