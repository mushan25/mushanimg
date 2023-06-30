package com.hzb.file.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageclassCO extends ClientObject {
    private Long id;
    @NotBlank(message = "图片分类名称不能为空")
    private String imgclassName;
    @JsonIgnore
    private Long userId;
    private LocalDateTime createTime;
}
