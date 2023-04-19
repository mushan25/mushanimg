package com.hzb.system.domain.menu.model.entities;

import com.alibaba.cola.domain.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Data
@NoArgsConstructor
@Entity
@Slf4j
public class Menu {
    private Long menuId;

    private String menuName;

    private Long parentId;

    private String perms;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    public Menu(Long menuId, String updateBy, LocalDateTime createTime){
        this.menuId = menuId;
        this.updateBy = updateBy;
        this.createTime = createTime;
    }
}
