package com.hzb.system.user.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Data
public class UserGetQry extends Command {
    private String userName;
}
