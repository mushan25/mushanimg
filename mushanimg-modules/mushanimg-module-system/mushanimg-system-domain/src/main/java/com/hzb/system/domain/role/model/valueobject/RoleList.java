package com.hzb.system.domain.role.model.valueobject;

import com.hzb.system.domain.role.model.entities.Role;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Data
public class RoleList {

    private List<Long> roleIds;
}
