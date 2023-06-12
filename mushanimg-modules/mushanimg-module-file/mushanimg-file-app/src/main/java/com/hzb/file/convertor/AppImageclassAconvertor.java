package com.hzb.file.convertor;

import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImageclassInfoQry;
import com.hzb.file.dto.ImageclassListQry;
import com.hzb.file.dto.ImgMoveClassCmd;
import com.hzb.file.dto.clientobject.ImageclassCO;
import com.hzb.file.dto.clientobject.ImageclassListCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/6/7
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AppImageclassAconvertor {
    AppImageclassAconvertor INSTANCT = Mappers.getMapper(AppImageclassAconvertor.class);

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

    /**
     * CO2Imageclass
     * @param imageclassCO ImageclassCO
     * @return Imageclass
     */
    Imageclass CO2Imageclass(ImageclassCO imageclassCO);

    /**
     * imageclassList2CO
     * @param imageclassList List<Imageclass>
     * @return List<ImageclassListCO>
     */
    List<ImageclassListCO> imageclassList2CO(List<Imageclass> imageclassList);

    /**
     * qry2Imageclass
     * @param imageclassInfoQry ImageclassInfoQry
     * @return Imageclass
     */
    Imageclass qry2Imageclass(ImageclassInfoQry imageclassInfoQry);

}
