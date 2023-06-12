package com.hzb.file.domain.imageclass.gateway;

import com.hzb.file.domain.imageclass.model.entities.Imageclass;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgclass(图片分类表)】的数据库操作Service
* @createDate 2023-06-01 17:22:33
*/
public interface ImageclassGateway {
    /**
     * 获取用户的图片分类列表
     * @param userId 用户id
     * @return 图片分类列表
     */
    List<Imageclass> getImageclassList(Long userId);

    /**
     * 获取图片分类信息
     * @param imageclass 图片分类信息
     * @return 图片分类信息
     */
    Imageclass getImageclssInfo(Imageclass imageclass);

    /**
     * 添加图片分类
     * @param imageclass 图片分类信息
     * @return 添加结果
     */
    boolean addImageclass(Imageclass imageclass);

    /**
     * 更新图片分类
     * @param imageclass 图片分类信息
     * @return 更新结果
     */
    boolean updateImageclass(Imageclass imageclass);

    /**
     * 删除图片分类
     * @param imageclassIds 图片分类id列表
     * @param userId 用户id
     * @return 删除结果
     */
    boolean deleteImageclass(List<Long> imageclassIds, Long userId);

    /**
     * 检查图片分类是否存在
     * @param imageclass 图片分类信息
     * @return 检查结果
     */
    boolean checkImageclassExist(Imageclass imageclass);

    /**
     * 获取图片分类下的图片id列表
     * @param imageclassIds 图片分类id列表
     * @return 图片id列表
     */
    List<Long> getImgIdsByImageclassId(List<Long> imageclassIds);

     /**
     * 删除图片分类下的图片
     * @param imgIds 图片id列表
     * @return 删除结果
     */
    boolean deleteImageclassByImgIds(List<Long> imgIds);

    /**
     * 删除图片分类下的图片
     * @param imageclassIds 图片分类id列表
     * @return 删除结果
     */
    boolean deleteImgByImageclassId(List<Long> imageclassIds);
}
