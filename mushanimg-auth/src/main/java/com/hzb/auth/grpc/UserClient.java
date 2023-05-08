package com.hzb.auth.grpc;

import com.hzb.base.grpc.utils.ProtobufBeanUtil;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.form.UserInfo;
import com.hzb.lib.user.proto.UserProto.*;
import com.hzb.lib.user.proto.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.IOException;
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

    public LoginUser getLoginUser(String username) throws IOException {
        UserGetRequest request = UserGetRequest.newBuilder().setUserName(username).build();
        UserGetReply response = null;
        try {
            response = stub.getUserInfoPerms(request);
        } catch (StatusRuntimeException e){
            log.info("-------- RPC failed: {}", e.getStatus());
        }

        if (null != response){
            return setLoginUser(response);
        }
        return null;
    }

    public Tuple2<Boolean, String> addUser(User user){
        UserAddRequest build = UserAddRequest.newBuilder().setUser(user).build();
        UserAddReply response = null;
        try {
            response = stub.addUser(build);
        }catch (StatusRuntimeException e){
            log.info("-------- RPC failed: {}", e.getStatus());
        }

        if (null != response){
            return Tuples.of(response.getAddResult(), response.getMsg());
        }
        return Tuples.of(false, "注册失败");
    }

    private LoginUser setLoginUser(UserGetReply response) throws IOException {
        LoginUser loginUser = ProtobufBeanUtil.toPojoBean(LoginUser.class, response);
        loginUser.setUserId(loginUser.getUser().getUserId());
        return loginUser;
    }
}
