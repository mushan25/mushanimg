package com.hzb.system.domain.menu.model.valueobject;

import com.hzb.system.domain.menu.model.entities.Menu;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Data
public class MenuList {
    private List<Long> menuIds;

}
