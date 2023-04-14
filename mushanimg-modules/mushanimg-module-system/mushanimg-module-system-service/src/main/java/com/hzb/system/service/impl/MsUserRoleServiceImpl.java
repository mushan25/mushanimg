package com.hzb.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.system.mapper.MsUserRoleMapper;
import com.hzb.system.model.po.MsUserRole;
import com.hzb.system.service.MsUserRoleService;
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
