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
    boolean upload2Minio(Image image);

    /**
     * 将图片信息存入数据库
     * @param image 图片信息
     * @return 存储结果
     */
    boolean addImg2Db(Image image);

    /**
     * 查询该图片是否已经入库
     * @param imgMd5Key 图片md5值
     * @return 查询结果
     */
    boolean selectImgByMd5(String imgMd5Key);

    /**
     * 获取指定用户的图片
     * @param image image
     * @return img list
     */
    List<Image> getImgList(Image image);

}
