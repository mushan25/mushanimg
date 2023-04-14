package com.hzb.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.users.mapper.MsRoleMenuMapper;
import com.hzb.users.model.po.MsRoleMenu;
import com.hzb.users.service.MsRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author hzb
 * @since 2023-04-14
 */
@Service
public class MsRoleMenuServiceImpl extends ServiceImpl<MsRoleMenuMapper, MsRoleMenu> implements MsRoleMenuService {

}
