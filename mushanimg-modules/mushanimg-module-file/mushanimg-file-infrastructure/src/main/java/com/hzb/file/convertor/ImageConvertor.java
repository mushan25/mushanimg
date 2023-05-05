package com.hzb.file.convertor;

import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import org.springframework.beans.BeanUtils;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
public class ImageConvertor {
    public static ImageDO toDataObject(Image image){
        ImageDO imageDO = new ImageDO();
        BeanUtils.copyProperties(image, imageDO);
        return imageDO;
    }

    public static ImageDO toDataObjectCreate(Image image){
        return toDataObject(image);
    }
}
