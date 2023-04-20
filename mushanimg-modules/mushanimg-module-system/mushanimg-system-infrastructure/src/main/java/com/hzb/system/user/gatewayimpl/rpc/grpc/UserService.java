package com.hzb.system.user.gatewayimpl.rpc.grpc;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.grpc.utils.ProtobufBeanUtil;
import com.hzb.lib.user.proto.UserProto.*;
import com.hzb.lib.user.proto.UserServiceGrpc;
import com.hzb.system.domain.ability.DomainService;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;

/**
 * @author hzb
 * @date 2023/4/19 21:27
 */
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final DomainService domainService;

    public UserService(DomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public void getUserInfoPerms(UserGetRequest request, StreamObserver<UserGetReply> responseObserver) {
        String userName = request.getName();

        AuthUser authUser = domainService.getAuthUserInfoByName(userName);

        UserGetReply.Builder builder = UserGetReply.newBuilder();
        try {
            ProtobufBeanUtil.toProtoBean(builder, authUser);
        } catch (IOException e){
            throw new SysException("pojo转换异常");
        }

        UserGetReply.Builder hello = UserGetReply.newBuilder()
                .setPermissions(Permissions.newBuilder().addPermissions("hello"));


        responseObserver.onNext(hello.build());
        responseObserver.onCompleted();
    }
}
