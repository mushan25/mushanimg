package com.hzb.auth.convertor;

import com.hzb.auth.form.RegisterBody;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.form.UserInfo;
import com.hzb.lib.user.proto.UserProto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    default LocalDateTime createLocalDateTime(String value){
        if (value != null && !value.isEmpty()) {
            // 如果值不为空字符串，则使用默认的LocalDateTime解析逻辑
            return LocalDateTime.parse(value);
        } else {
            // 如果值为空字符串，则返回自定义的默认值，例如当前时间
            return null;
        }
    }

    default LocalDate createLocalDate(String value){
        if (value != null && !value.isEmpty()) {
            // 如果值不为空字符串，则使用默认的LocalDateTime解析逻辑
            return LocalDate.parse(value);
        } else {
            // 如果值为空字符串，则返回自定义的默认值，例如当前时间
            return null;
        }
    }
}
