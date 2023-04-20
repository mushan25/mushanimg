package com.hzb.service;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author hzb
 * @date 2023/4/20 0:39
 */
@Service
@Slf4j
public class GrpcClientTest {

    @GrpcClient("system-service")
    UserServiceGrpc.UserServiceBlockingStub  stub;

    public void test(String userName){
        UserGetRequest request =UserGetRequest.newBuilder().setName(userName).build();
        UserGetReply response = null;
        try {
            response = stub.getUserInfoPerms(request);
        }catch (StatusRuntimeException e){
            System.out.printf("RPC failed: " + e.getStatus());
        }
        if (response != null) {
            User user = response.getUser();
            Permissions permissions = response.getPermissions();
        }
        log.info("test: " + response.getPermissions());
    }

}
