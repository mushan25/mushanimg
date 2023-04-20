package com.hzb.service;

import com.hzb.proto.GreeterGrpc;
import com.hzb.proto.ProtoDemo.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.logging.Level;


/**
 * @author hzb
 * @date 2023/4/19 0:42
 */
@Service
@Slf4j
public class GrpcClientService {
    @GrpcClient("system-service")
    GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    public void greeter(){
        HelloRequest request = HelloRequest.newBuilder().setName("hzb").build();
        HelloReply response = null;
        try {
            response = greeterBlockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
        }
        log.info("Greeting: " + response.getMessage());
//        try {
//            response = greeterBlockingStub.sayHelloAgain(request);
//        } catch (StatusRuntimeException e) {
//            log.warn("RPC failed: {0}", e.getStatus());
//            return;
//        }
//        log.info("Greeting: " + response.getMessage());

    }
}
