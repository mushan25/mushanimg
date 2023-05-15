package com.hzb.system.convertor;

import com.hzb.base.core.utils.CheckUtils;
import com.hzb.lib.user.proto.UserProto;
import com.hzb.lib.user.proto.UserProto.UserAddRequest;
import com.hzb.lib.user.proto.UserProto.UserGetReply;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.gatewayimpl.database.dataobject.UserDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
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


    /**
     * DO2User
     * @param user User
     * @param userDO UserDO
     */
    @Mapping(target = "password.password", source = "password")
    void DO2User(@MappingTarget User user, UserDO userDO);

    /**
     * userDOList2UserList
     * @param userDOS List<UserDO>
     * @return List<User>
     */
    List<User> userDOList2UserList(List<UserDO> userDOS);

    /**
     * user2Grpc
     * @param user user
     * @return grpc
     */
    UserProto.User user2Grpc(User user);

    /**
     * Grpc2User
     * @param request Grpc
     * @return User
     */
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "nickName", source = "user.nickName")
    @Mapping(target = "password", source = "user.password.password")
    User grpc2User(UserAddRequest request);

    /**
     * AddUserResult2Grpc
     * @param result addResult
     * @return grpc
     */
    @Mapping(target = "addResult", source = "t1")
    @Mapping(target = "msg", source = "t2")
    UserProto.UserAddReply addUserResult2Grpc(Tuple2<Boolean, String> result);

    /**
     * AuthUser2Grpc
     * @param authUser User
     * @return Grpc
     */
    default UserGetReply authUser2Grpc(AuthUser authUser){
        if ( authUser == null ) {
            return null;
        }

        UserProto.UserGetReply.Builder userGetReply = UserProto.UserGetReply.newBuilder();

        CheckUtils.isPresentRunnable(authUser.getRoleKeys())
                .presentHandler(()-> userGetReply.addAllRoleKeys(authUser.getRoleKeys()));

        CheckUtils.isPresentRunnable(authUser.getPermissions())
                .presentHandler(() -> userGetReply.addAllPermissions(authUser.getPermissions()));

        CheckUtils.isPresentRunnable(authUser.getUser())
                .presentHandler(() -> userGetReply.setUser(user2Grpc(authUser.getUser())));

        return userGetReply.build();
    }
}
