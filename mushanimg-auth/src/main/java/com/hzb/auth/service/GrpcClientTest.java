package com.hzb.auth.service;

import com.hzb.base.core.constant.HttpStatus;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.grpc.utils.ProtobufBeanUtil;
import com.hzb.lib.user.proto.UserProto.*;
import com.hzb.lib.user.proto.UserServiceGrpc;
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

    public AjaxResult test(String userName){
        UserGetRequest request =UserGetRequest.newBuilder().setName(userName).build();
        UserGetReply response = null;
        try {
            response = stub.getUserInfoPerms(request);
        }catch (StatusRuntimeException e){
            log.warn("RPC failed: {0}", e.getStatus());
        }

        User user = response.getUser();
        Permissions permissions = response.getPermissions();

        return new AjaxResult(HttpStatus.SUCCESS, "sucess", user.toString());
    }

}
