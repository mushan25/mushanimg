package com.hzb.file.convertor;

import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/6/9
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ImageclassConvertor {
    ImageclassConvertor INSTANCT = Mappers.getMapper(ImageclassConvertor.class);

    /**
     * DO2Imageclass
     * @param imageclassDO ImageclassDO
     * @return Imageclass
     */
    Imageclass DO2Imageclass(ImageclassDO imageclassDO);

    /**
     * DOs2Imageclasses
     * @param imageclassDOs imageclassDOs
     * @return List<Imageclass>
     */
    List<Imageclass> DOs2Imageclasses(List<ImageclassDO> imageclassDOs);
}
