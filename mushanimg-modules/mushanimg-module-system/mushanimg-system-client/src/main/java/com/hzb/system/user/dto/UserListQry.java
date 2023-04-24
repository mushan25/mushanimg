package com.hzb.system.user.dto;

import com.hzb.base.core.web.page.PageParam;
import com.hzb.system.user.dto.clientobject.UserCO;
import lombok.Data;

/**
 * @author: hzb
 * @Date: 2023/4/24
 */
@Data
public class UserListQry {
    private PageParam pageParam;
    private UserCO userCO;

}
