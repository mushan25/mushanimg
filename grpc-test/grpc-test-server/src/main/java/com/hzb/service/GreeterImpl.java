package com.hzb.service;

import com.hzb.api.GreeterGrpc;
import com.hzb.proto.ProtoDemo;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author hzb
 * @date 2023/4/18 23:53
 */
@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(ProtoDemo.HelloRequest request, StreamObserver<ProtoDemo.HelloReply> responseObserver) {
        ProtoDemo.HelloReply reply = ProtoDemo.HelloReply.newBuilder().setMessage("hello again" + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
