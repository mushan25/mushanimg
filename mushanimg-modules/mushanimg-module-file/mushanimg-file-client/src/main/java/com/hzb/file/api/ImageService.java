package com.hzb.file.api;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.dto.clientobject.ImageCO;

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

    /**
     * 获取图片list
     * @param imgListQry 图片筛选条件
     * @return 图片分页信息
     */
    PageResponse<ImageCO> getImageList(ImgListQry imgListQry);
}
