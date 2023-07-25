package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageclassEditCmd extends Command {
    @NotNull(message = "请选择图片分类")
    private Long id;
    @NotBlank(message = "图片分类名称不能为空")
    @Size(max = 5, message = "图片分类名称不能超过5个字符")
    private String imgclassName;
    @JsonIgnore
    private Long userId;
}
