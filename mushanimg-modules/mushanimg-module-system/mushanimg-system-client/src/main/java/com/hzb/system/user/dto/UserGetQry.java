package com.hzb.system.user.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGetQry extends Command {
    private String userName;
}
