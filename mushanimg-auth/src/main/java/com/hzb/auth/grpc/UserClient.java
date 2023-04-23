package com.hzb.auth.grpc;

import com.hzb.auth.form.LoginUser;
import com.hzb.lib.user.proto.UserProto.*;
import com.hzb.lib.user.proto.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Service
@Slf4j
public class UserClient {

    @GrpcClient("system-service")
    UserServiceGrpc.UserServiceBlockingStub stub;

    public LoginUser getLoginUser(String username){
        UserGetRequest request = UserGetRequest.newBuilder().setUserName(username).build();
        UserGetReply response = null;
        try {
            response = stub.getUserInfoPerms(request);
        } catch (StatusRuntimeException e){
            log.info("-------- RPC failed: {}", e.getStatus());
        }

        if (response != null){
            return setLoginUser(response);
        }
        return null;
    }

    private LoginUser setLoginUser(UserGetReply response){
        User user = response.getUser();
        Set<String> permissions = new HashSet<>(response.getPermissions().getPermissionsList());
        return new LoginUser(user.getUserId(), user, permissions);
    }
}
