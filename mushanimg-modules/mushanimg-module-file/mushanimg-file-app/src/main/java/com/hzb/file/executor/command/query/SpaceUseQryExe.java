package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.file.api.UserClientService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.dto.clientobject.SpaceUseCO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: hzb
 * @Date: 2023/7/25
 */
@AllArgsConstructor
@Component
@Slf4j
public class SpaceUseQryExe {
    private final ImageGateway imageGateway;
    private static final BigDecimal MB = BigDecimal.valueOf(1024 * 1024);
    private static final BigDecimal GB = BigDecimal.valueOf(1024 * 1024 * 1024);
    private final UserClientService userClientService;
    public SingleResponse<SpaceUseCO> execute(Long userId){
        BigDecimal userUsedSize = BigDecimal.valueOf(imageGateway.getUserUsedSize(userId));
        BigDecimal uploadSize = BigDecimal.valueOf(userClientService.getUserInfo(userId).getUploadSize());
        String totalSize;
        String usedSize;
        if (uploadSize.multiply(MB).compareTo(GB) < 0) {
            totalSize = uploadSize + "MB";
        }else {
            totalSize = uploadSize.multiply(MB).divide(GB, 2, RoundingMode.HALF_DOWN) + "GB";
        }

        if (userUsedSize.compareTo(GB) < 0) {
            usedSize = userUsedSize.divide(MB, 2, RoundingMode.HALF_DOWN) + "MB";
        }else {
            usedSize = userUsedSize.divide(GB, 2, RoundingMode.HALF_DOWN) + "GB";
        }

        SpaceUseCO spaceUseCO = SpaceUseCO.builder()
                .totalSize(totalSize)
                .usedSize(usedSize)
                .build();

        return SingleResponse.of(spaceUseCO);
    }
}
