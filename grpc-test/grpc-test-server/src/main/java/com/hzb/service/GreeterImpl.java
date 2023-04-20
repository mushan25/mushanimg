package com.hzb.service;

import com.hzb.proto.GreeterGrpc;
import com.hzb.proto.ProtoDemo.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author hzb
 * @date 2023/4/18 23:53
 */
@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("hello again" + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
