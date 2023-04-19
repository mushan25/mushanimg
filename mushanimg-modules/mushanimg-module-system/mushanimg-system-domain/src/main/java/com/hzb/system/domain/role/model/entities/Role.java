package com.hzb.system.domain.role.model.entities;

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
public class Role {
    private Long roleId;
    private String roleName;

    private String roleKey;

    private String status;

    private String delFlag;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;

    public Role(Long roleId, String updateBy, LocalDateTime updateTime){
        this.roleId = roleId;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

}
