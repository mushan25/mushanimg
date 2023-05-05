package com.hzb.file.api;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.ImgUploadCmd;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
public interface ImageService {
    /**
     * 上传图片
     * @param imgUploadCmd 图片信息
     * @return 返回结果
     */
    AjaxResult uploadImg(ImgUploadCmd imgUploadCmd);
}
