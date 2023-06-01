package com.hzb.file.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCO extends ClientObject {
    /**
     * 主键
     */
    private Long id;

    /**
     * 图片名
     */
    private String imgName;

    /**
     * 图片链接
     */
    private String imgurl;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
