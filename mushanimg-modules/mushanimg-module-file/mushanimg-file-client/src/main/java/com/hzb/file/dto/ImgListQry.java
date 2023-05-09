package com.hzb.file.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
