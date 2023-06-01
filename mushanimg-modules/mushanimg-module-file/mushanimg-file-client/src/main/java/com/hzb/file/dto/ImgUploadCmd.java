package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ImgUploadCmd extends Command {
    /**
     * 图片名
     */
    private String imgName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * 图片本地地址
     */
    private String localFilePath;
}
