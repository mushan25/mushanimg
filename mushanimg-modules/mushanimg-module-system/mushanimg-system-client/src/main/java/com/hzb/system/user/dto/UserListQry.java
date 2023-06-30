package com.hzb.system.user.dto;

import com.alibaba.cola.dto.Command;
import com.hzb.system.user.dto.clientobject.UserCO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/4/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListQry extends Command {
    private UserCO userCO;

}
