package com.hzb.file.api;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.dto.clientobject.ImageListCO;
import com.hzb.file.dto.clientobject.SpaceUseCO;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: hzb
 * @Date: 2023/5/5
 */
public interface ImageService {
    /**
     * 上传图片
     * @param img 图片
     * @return 返回结果
     */
    AjaxResult uploadImg(MultipartFile img);

    /**
     * 获取图片list
     * @param imgListQry 图片筛选条件
     * @return 图片分页信息
     */
    PageResponse<ImageListCO> getImageList(ImgListQry imgListQry);

    /**
     * 获取图片信息
     * @param imgInfoQry 图片信息查询条件
     * @return 图片信息
     */
    SingleResponse<ImageCO> getImageInfo(ImgInfoQry imgInfoQry);

    /**
     * 编辑图片信息
     * @param imgInfoEditCmd 图片信息
     * @return 编辑结果
     */
    AjaxResult editImageInfo(ImgInfoEditCmd imgInfoEditCmd);

    /**
     * 删除图片
     * @param imgRemoveCmd 图片信息
     * @return 删除结果
     */
    AjaxResult removeImage(ImgRemoveCmd imgRemoveCmd);

    /**
     * 移动图片到其他分类
     * @param imgMoveClassCmd 图片信息
     * @return 移动结果
     */
    AjaxResult moveImage2OtherClass(ImgMoveClassCmd imgMoveClassCmd);

    /**
     * 上传头像
     * @param avatar 头像
     * @return 结果
     */
    AjaxResult uploadAvatar(MultipartFile avatar);

    /**
     * 获取空间使用情况
     * @param userId 用户id
     * @return 空间使用情况
     */
    SingleResponse<SpaceUseCO> getSpaceUseInfo(Long userId);
}
