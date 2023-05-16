package com.hzb.file.convertor;

import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ImageConvertor {
    ImageConvertor INSTANCT = Mappers.getMapper(ImageConvertor.class);

    /**
     * DO2Image
     * @param imageDO ImageDO
     * @return Image
     */
    Image DO2Image(ImageDO imageDO);

    /**
     * image2DO
     * @param image Image
     * @return ImageDO
     */
    ImageDO image2DO(Image image);

    /**
     * imageDOs2imageList
     * @param imageDOS List<ImageDO>
     * @return List<Image>
     */
    List<Image> imageDOs2imageList(List<ImageDO> imageDOS);
}
