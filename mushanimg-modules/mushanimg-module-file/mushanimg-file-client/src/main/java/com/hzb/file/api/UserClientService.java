package com.hzb.file.api;

import com.hzb.file.dto.grpc.UploadUserInfo;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
public interface UserClientService {
    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UploadUserInfo getUserInfo(Long userId);
}
