package com.hzb.file.executor.command;

import com.hzb.base.core.annotation.AccessModeAnnotation;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.UserClientService;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgRemoveCmd;
import com.hzb.file.dto.grpc.UploadUserInfo;
import com.hzb.file.executor.service.ImageStrategy;
import com.hzb.file.executor.service.mq.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
@Component
@Slf4j
@AccessModeAnnotation(taxType = AccessMode.NORM_USER)
public class UserImgUploadCmdExe extends ImageStrategy {
    @Value("${minio.bucket.files}")
    private String bucketName;
    private final UserClientService userClientService;
    private final DomainService domainService;
    public UserImgUploadCmdExe(ImageGateway imageGateway, RedissonClient redissonClient, RocketMqProducer rocketMqProducer, UserClientService userClientService, DomainService domainService) {
        super(imageGateway, redissonClient, rocketMqProducer);
        this.userClientService = userClientService;
        this.domainService = domainService;
    }

    @Override
    public AjaxResult execute(MultipartFile img) {
        Long useId = SecurityUtils.getUserId();

        if (img.getSize() > Constants.USER_UPLOAD_SIZE) {
            return AjaxResult.error("图片大小超过限制");
        }

        UploadUserInfo userInfo = userClientService.getUserInfo(useId);
        long totalSize = userInfo.getUploadSize() * Constants.MB_SIZE;
        long count = totalSize - imageGateway.getUserUsedSize(useId);
        if (img.getSize() > count) {
            throw new ServiceException("存储空间不足");
        }

        File tempFile = FileUtils.transferFile(img, tempFilePath);
        Image image = DomainFactory.initImage(bucketName, img.getOriginalFilename(), img.getSize(), tempFile.getAbsolutePath());
        image.setUserId(useId)
                .setObjectName(image.initObjectName(userInfo.getUserName()));

        return uploadImage(image, tempFile);
    }

    @Override
    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd) {
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), SecurityUtils.getUserId())) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
