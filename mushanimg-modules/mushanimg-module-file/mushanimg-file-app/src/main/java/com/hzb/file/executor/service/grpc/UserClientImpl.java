package com.hzb.file.executor.service.grpc;

import com.hzb.file.api.UserClientService;
import com.hzb.file.convertor.GrpcConvertor;
import com.hzb.file.dto.grpc.UploadUserInfo;
import com.hzb.lib.user.proto.UserProto;
import com.hzb.lib.user.proto.UserServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
@Service
@Slf4j
public class UserClientImpl implements UserClientService {

    @GrpcClient("system-service")
    UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public UploadUserInfo getUserInfo(Long userId) {
        UserProto.UploadUserInfoRequest request = UserProto.UploadUserInfoRequest.newBuilder().setUserId(userId).build();
        UserProto.UploadUserInfoReply response = null;
        try {
            response = stub.uploadUserInfo(request);
        } catch (Exception e){
            log.info("-------- RPC failed: {}", e.getMessage());
        }
        return GrpcConvertor.INSTANCT.grpc2UploadUserInfo(response);
    }
}
