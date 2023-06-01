package com.hzb.file.convertor;

import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.dto.clientobject.ImageListCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     * imageList2ImageCOList
     * @param images List<Image>
     * @return List<Image>
     */
    List<ImageCO> imageList2ImageCOList(List<Image> images);

    /**
     * image2ImageListCO
     * @param image Image
     * @return ImageListCO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "imgurl", source = "imgurl")
    ImageListCO image2ImageListCO(Image image);

    /**
     * imageList2ImageListCOList
     * @param images List<Image>
     * @return List<ImageListCO>
     */
    List<ImageListCO> imageList2ImageListCOList(List<Image> images);

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
