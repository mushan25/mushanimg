package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImgMoveClassCmd extends Command {
    @NotEmpty(message = "图片不能为空")
    @Size(max = 20, message = "图片数量不能超过20张")
    private List<Long> imgIds;
    private Long imgclassId;
    @JsonIgnore
    private Long userId;
}
