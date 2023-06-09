package com.hzb.system.user.gatewayimpl.rpc.grpc;

import com.hzb.lib.user.proto.UserProto.UserAddReply;
import com.hzb.lib.user.proto.UserProto.UserAddRequest;
import com.hzb.lib.user.proto.UserProto.UserGetReply;
import com.hzb.lib.user.proto.UserProto.UserGetRequest;
import com.hzb.lib.user.proto.UserServiceGrpc;
import com.hzb.system.convertor.UserConvertor;
import com.hzb.system.domain.ability.DomainService;
import com.hzb.system.domain.user.gateway.UserGateway;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author hzb
 * @date 2023/4/19 21:27
 */
@AllArgsConstructor
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final DomainService domainService;

    private final UserGateway userGateway;

    @Override
    public void addUser(UserAddRequest request, StreamObserver<UserAddReply> responseObserver) {
        responseObserver.onNext(UserConvertor.INSTANCT
                .addUserResult2Grpc(userGateway.registerUser(UserConvertor.INSTANCT.grpc2User(request))));
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfoPerms(UserGetRequest request, StreamObserver<UserGetReply> responseObserver) {
        responseObserver.onNext(UserConvertor.INSTANCT
                .authUser2Grpc(domainService.getAuthUserInfoByName(request.getUserName())));
        responseObserver.onCompleted();
    }
}
