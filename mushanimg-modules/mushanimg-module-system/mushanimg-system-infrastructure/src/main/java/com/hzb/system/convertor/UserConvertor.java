package com.hzb.system.convertor;

import com.google.protobuf.ProtocolStringList;
import com.hzb.lib.user.proto.UserProto;
import com.hzb.lib.user.proto.UserProto.UserAddRequest;
import com.hzb.lib.user.proto.UserProto.UserGetReply;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.gatewayimpl.database.dataobject.UserDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Mapper(uses = BaseConvertor.class, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
        , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {

    UserConvertor INSTANCT = Mappers.getMapper(UserConvertor.class);

    /**
     * User2UserDO
     * @param user User
     * @return UserDO
     */
    @Mapping(target = "password", source = "password.password")
    UserDO user2DO(User user);

//    /**
//     * AuthUser2Grpc
//     * @param authUser AuthUser
//     * @return Grpc
//     */
//    @Mapping(source = "roleKeys", target = "roleKeysList")
//    @Mapping(source = "permissions", target = "permissionsList")
//    UserGetReply authUser2Grpc(AuthUser authUser);

    UserProto.User user2Grpc(User user);

    default UserGetReply authUser2Grpc(AuthUser authUser){
        if ( authUser == null ) {
            return null;
        }

        UserProto.UserGetReply.Builder userGetReply = UserProto.UserGetReply.newBuilder();

        if ( authUser.getRoleKeys() != null ) {
            userGetReply.addAllRoleKeys( authUser.getRoleKeys() );
        }
        if ( authUser.getPermissions() != null ) {
            userGetReply.addAllPermissions( authUser.getPermissions() );
        }
        if ( authUser.getUser() != null ) {
            userGetReply.setUser( user2Grpc( authUser.getUser() ) );
        }

        return userGetReply.build();
    }

    /**
     * Grpc2User
     * @param request Grpc
     * @return User
     */
    User grpc2User(UserAddRequest request);

//    public static UserDO toDataObject(User user){
//        UserDO userDO = new UserDO();
//        BeanUtils.copyProperties(user, userDO);
//        userDO.setPassword(user.getPassword().getPassword());
//        return userDO;
//    }
//
//    public static UserDO toDataObjectForCreate(User user){
//        return toDataObject(user);
//    }
//
//    public static UserDO toDataObjectForUpdate(User user){
//        return toDataObject(user);
//    }
//    public static UserGetReply User2Grpc(AuthUser authUser){
//        UserGetReply.Builder builder = UserGetReply.newBuilder();
//        try {
//            ProtobufBeanUtil.toProtoBean(builder, authUser);
//        } catch (IOException e) {
//            throw new SysException("pojo转换异常");
//        }
//        return builder.build();
//    }
//
//    public static UserAddReply AddUserResult2Grpc(Tuple2<Boolean, String> result){
//        UserAddReply.Builder builder = UserAddReply.newBuilder();
//        builder.setAddResult(result.getT1())
//                .setMsg(result.getT2());
//        return builder.build();
//    }
//    public static User Grpc2User(UserAddRequest request){
//        try {
//            return ProtobufBeanUtil.toPojoBean(User.class, request.getUser());
//        }catch (IOException e){
//            throw new SysException("pojo转换异常");
//        }
//    }
}
