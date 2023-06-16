package com.hzb.file.api;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.ImgRemoveCmd;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: hzb
 * @Date: 2023/6/14
 */
public interface ImageStrategy {
    /**
     * 上传图片
     * @param imgs 图片
     * @return 上传结果
     */
    AjaxResult execute(MultipartFile[] imgs);

    /**
     * 删除图片
     * @param imgRemoveCmd 图片信息
     * @return 删除结果
     */
    AjaxResult execute(ImgRemoveCmd imgRemoveCmd);
}
