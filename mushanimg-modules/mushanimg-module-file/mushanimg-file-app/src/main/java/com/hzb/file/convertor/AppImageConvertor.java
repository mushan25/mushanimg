package com.hzb.file.convertor;

import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.dto.clientobject.ImageCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/16
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AppImageConvertor {
    AppImageConvertor INSTANCT = Mappers.getMapper(AppImageConvertor.class);

    /**
     * image2CO
     * @param image Image
     * @return ImageCO
     */
    ImageCO image2CO(Image image);

    /**
     * CO2Image
     * @param imageCO ImageCO
     * @return Image
     */
    Image CO2Image(ImageCO imageCO);

    /**
     * imageCOs2ImageCOList
     * @param images List<Image>
     * @return List<Image>
     */
    List<ImageCO> imageCOs2ImageCOList(List<Image> images);

    /**
     * qry2Image
     * @param imgListQry ImgListQry
     * @return Image
     */
    Image qry2Image(ImgListQry imgListQry);

    /**
     * cmd2Image
     * @param imgUploadCmd ImgUploadCmd
     * @return Image
     */
    Image cmd2Image(ImgUploadCmd imgUploadCmd);
}
