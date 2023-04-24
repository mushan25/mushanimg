package com.hzb.system.user.gatewayimpl.rpc.grpc;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.grpc.utils.ProtobufBeanUtil;
import com.hzb.lib.user.proto.UserProto.*;
import com.hzb.lib.user.proto.UserServiceGrpc;
import com.hzb.system.domain.ability.DomainService;
import com.hzb.system.domain.user.gateway.UserGateway;
import com.hzb.system.domain.user.model.aggregates.AuthUser;
import com.hzb.system.domain.user.model.entities.User;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.util.Map;

/**
 * @author hzb
 * @date 2023/4/19 21:27
 */
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final DomainService domainService;

    private final UserGateway userGateway;

    public UserService(DomainService domainService, UserGateway userGateway) {
        this.domainService = domainService;
        this.userGateway = userGateway;
    }

    @Override
    public void addUser(UserAddRequest request, StreamObserver<UserAddReply> responseObserver) {

        UserAddReply.Builder replyBuilder = UserAddReply.newBuilder();
        try {
            User user = ProtobufBeanUtil.toPojoBean(User.class, request.getUser());
            Tuple2<Boolean, String> tuple = userGateway.registerUser(user);
            replyBuilder.setAddResult(tuple.getT1())
                    .setMsg(tuple.getT2());
        }catch (IOException e){
            throw new SysException("pojo转换异常");
        }
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfoPerms(UserGetRequest request, StreamObserver<UserGetReply> responseObserver) {
        String userName = request.getUserName();

        AuthUser authUser = domainService.getAuthUserInfoByName(userName);

        UserGetReply.Builder builder = UserGetReply.newBuilder();
        try {
            ProtobufBeanUtil.toProtoBean(builder, authUser);
        } catch (IOException e){
            throw new SysException("pojo转换异常");
        }


        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }


}
