package com.hzb.file.domain.image.gateway;

import com.hzb.file.domain.image.model.entities.Image;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Service
* @createDate 2023-05-05 14:34:45
*/
public interface ImageGateway {
    /**
     * 上传图片到minio
     * @param image 图片信息
     * @return 上传结果
     */
    Image upload2Minio(Image image);

    /**
     * 将图片信息存入数据库
     * @param image 图片信息
     * @return 存储结果
     */
    boolean addImg2Db(Image image);

    /**
     * 查询该图片是否已经入库
     * @param image image
     * @return 查询结果
     */
    Image selectImgByMd5(Image image);

    /**
     * 获取指定用户的图片
     * @param image image
     * @param imgIds imgIds
     * @return img list
     */
    List<Image> getImgList(Image image, List<Long> imgIds);

    /**
     * 获取图片信息
     * @param image image
     * @return image
     */
    Image getImgInfo(Image image);

    /**
     * 更新图片信息
     * @param image image
     * @return update result
     */
    boolean updateImgInfo(Image image);

    /**
     * 删除图片
     * @param imgIds imgIds
     * @param userId userId
     * @return delete result
     */
    boolean deleteImgDb(List<Long> imgIds, Long userId);

    /**
     * 删除图片
     * @param objectNameList objectNameList
     * @return delete result
     */
    List<String> deleteImgMinio(List<String> objectNameList);

    /**
     * 根据图片id获取图片名称
     * @param imgIds imgIds
     * @param userId userId
     * @return imgNameList
     */
    List<Image> selectObjetNameByIds(List<Long> imgIds, Long userId);

    /**
     * 移动图片到其他分类
     * @param imageclassId imageclassId
     * @param imgDataId imgdataId
     * @return move result
     */
    boolean moveImg2OtherClass(Long imgDataId, Long imageclassId);

    /**
     * 根据图片id列表删除图片
     * @param imgIds imgIds
     * @return delete result
     */
    boolean deleteImgByImgIds(List<Long> imgIds);

    /**
     * 获取用户已使用的空间大小
     * @param userId userId
     * @return delete result
     */
    Long getUserUsedSize(Long userId);

    /**
     * 根据版本id获取图片信息
     * @param versionIds versionIds
     * @return image list
     */
    void restoreImageByVersionId(List<Image> versionIds);
}
