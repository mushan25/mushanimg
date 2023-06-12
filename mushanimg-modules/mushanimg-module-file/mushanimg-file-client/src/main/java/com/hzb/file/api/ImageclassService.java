package com.hzb.file.api;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageclassCO;
import com.hzb.file.dto.clientobject.ImageclassListCO;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
public interface ImageclassService {
    /**
     * 添加图片分类
     * @param imageclassAddCmd 图片分类信息
     * @return 添加结果
     */
    AjaxResult addImageclss(ImageclassAddCmd imageclassAddCmd);

    /**
     * 编辑图片分类
     * @param imageclassEditCmd 图片分类信息
     * @return 编辑结果
     */
    AjaxResult editImageclss(ImageclassEditCmd imageclassEditCmd);

    /**
     * 删除图片分类
     * @param imageclassRemoveCmd 图片分类信息
     * @return 删除结果
     */
    AjaxResult removeImageclss(ImageclassRemoveCmd imageclassRemoveCmd);

    /**
     * 获取图片分类列表
     * @param imageclassListQry 图片分类查询条件
     * @return 图片分类列表
     */
    PageResponse<ImageclassListCO> getImageclassList(ImageclassListQry imageclassListQry);

    /**
     * 获取图片分类信息
     * @param imageclassInfoQry 图片分类查询条件
     * @return 图片分类信息
     */
    SingleResponse<ImageclassCO> getImageclassInfo(ImageclassInfoQry imageclassInfoQry);
}
