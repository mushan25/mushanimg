package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImgListQry extends Command {
    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 图片名
     */
    private String imgName;

    /**
     * img类型
     */
    private String imgType;

    /**
     * 图片分类id
     */
    private List<Long> imgclassIds;
}
