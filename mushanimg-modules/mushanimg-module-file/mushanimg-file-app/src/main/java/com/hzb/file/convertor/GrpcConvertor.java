package com.hzb.file.convertor;

import com.hzb.file.dto.grpc.UploadUserInfo;
import com.hzb.lib.user.proto.UserProto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface GrpcConvertor {
    GrpcConvertor INSTANCT = Mappers.getMapper(GrpcConvertor.class);

    /**
     * grpc2UploadUserInfo
     * @param uploadUserInfoReply UserProto.UploadUserInfoReply
     * @return UploadUserInfo
     */
    UploadUserInfo grpc2UploadUserInfo(UserProto.UploadUserInfoReply uploadUserInfoReply);


}
