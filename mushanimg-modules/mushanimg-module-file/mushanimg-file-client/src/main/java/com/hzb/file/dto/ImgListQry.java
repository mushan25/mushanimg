package com.hzb.file.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzb.file.dto.clientobject.ImageCO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@Data
public class ImgListQry {
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
}
