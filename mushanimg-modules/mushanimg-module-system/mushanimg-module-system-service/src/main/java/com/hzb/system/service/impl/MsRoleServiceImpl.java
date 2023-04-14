package com.hzb.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.users.mapper.MsRoleMapper;
import com.hzb.users.model.po.MsRole;
import com.hzb.users.service.MsRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author hzb
 * @since 2023-04-14
 */
@Service
public class MsRoleServiceImpl extends ServiceImpl<MsRoleMapper, MsRole> implements MsRoleService {

}
