package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImgInfoEditCmd extends Command {
    @NotNull(message = "图片不能为空")
    private Long id;
    @JsonIgnore
    private Long userId;
    private String imgName;
    private String remark;
}
