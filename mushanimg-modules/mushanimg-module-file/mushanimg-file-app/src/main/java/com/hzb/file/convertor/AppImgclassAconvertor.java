package com.hzb.file.convertor;

import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImgMoveClassCmd;
import com.hzb.file.dto.clientobject.ImageclassCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author: hzb
 * @Date: 2023/6/7
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AppImgclassAconvertor {
    AppImgclassAconvertor INSTANCT = Mappers.getMapper(AppImgclassAconvertor.class);

    /**
     * cmd2Imageclass
     * @param imgMoveClassCmd ImgMoveClassCmd
     * @return Imageclass
     */
    @Mapping(target = "id", source = "imgclassId")
    Imageclass cmd2Imageclass(ImgMoveClassCmd imgMoveClassCmd);

    /**
     * imageclass2CO
     * @param imageclass Imageclass
     * @return ImageclassCO
     */
    ImageclassCO imageclass2CO(Imageclass imageclass);
}
