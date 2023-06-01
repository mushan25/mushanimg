package com.hzb.file.domain.imageclass.model.entities;

import com.alibaba.cola.domain.Entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@Data
@Entity
public class Imageclass {
    /**
     * id
     */
    private Long id;

    /**
     * 图片分类名称
     */
    private String imgclassName;

    /**
     *
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
