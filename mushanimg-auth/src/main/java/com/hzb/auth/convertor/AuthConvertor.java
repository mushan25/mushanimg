package com.hzb.auth.convertor;

import com.hzb.auth.form.RegisterBody;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.form.UserInfo;
import com.hzb.lib.user.proto.UserProto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author: hzb
 * @Date: 2023/5/15
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthConvertor {

    AuthConvertor INSTANCT = Mappers.getMapper(AuthConvertor.class);
    /**
     * grpc2UserInfo
     * @param user UserProto.User
     * @return UserInfo
     */
    UserInfo grpc2UserInfo(UserProto.User user);

    /**
     * grpc2LoginUser
     * @param response UserProto.UserGetReply
     * @return LoginUser
     */
    @Mapping(target = "roleKeys", source = "roleKeysList")
    @Mapping(target = "permissions", source = "permissionsList")
    LoginUser grpc2LoginUser(UserProto.UserGetReply response);

    /**
     * registerBody2Grpc
     * @param registerBody RegisterBody
     * @return UserProto.UserAddRequest
     */
    @Mapping(target = "user.userName", source = "username")
    @Mapping(target = "user.password.password", source = "password")
    UserProto.UserAddRequest registerBody2Grpc(RegisterBody registerBody);
}
