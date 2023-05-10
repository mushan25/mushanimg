package com.hzb.system.convertor;

import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.Collection;

/**
 * @author: hzb
 * @Date: 2023/5/10
 */
@Mapper
public class BaseConvertor {
    @ObjectFactory
    ProtocolStringList toProtocolStringList(Collection<String> collection){
        return new LazyStringArrayList(collection.size());
    }
}
