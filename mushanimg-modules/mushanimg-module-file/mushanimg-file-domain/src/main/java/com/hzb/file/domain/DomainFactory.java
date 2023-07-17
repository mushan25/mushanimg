package com.hzb.file.domain;

import com.hzb.base.core.constant.Constants;
import com.hzb.file.domain.image.model.entities.Image;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
public class DomainFactory {
    public static Image getImage(){
        return new Image();
    }
    public static Image initImage(String bucketName,String imgName, Long size, String absolutePath){
        Image image = Image.builder()
                .imgName(imgName)
                .size(size)
                .localFilePath(absolutePath)
                .bucketName(bucketName)
                .build();
        return image.setImgType(image.initImgType())
                .setMimeType(image.initMimeType())
                .setMd5Key(image.initMd5Key());
    }
}
