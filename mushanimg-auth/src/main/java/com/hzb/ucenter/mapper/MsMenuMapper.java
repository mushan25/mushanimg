package com.hzb.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.ucenter.model.po.MsMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author hzb
 * @since 2023-04-11
 */
public interface MsMenuMapper extends BaseMapper<MsMenu> {
    /**
     * 根据用户id获取对应权限
     * @param userId 用户id
     * @return 用户权限
     */
    @Select("SELECT * FROM ms_menu WHERE menu_id IN " +
            "(SELECT menu_id FROM ms_role_menu WHERE role_id IN " +
            "(SELECT role_id FROM ms_user_role WHERE user_id = #{userId} )" +
            ")"
    )
    List<MsMenu> selectPermissionByUserId(@Param("userId") String userId);
}
