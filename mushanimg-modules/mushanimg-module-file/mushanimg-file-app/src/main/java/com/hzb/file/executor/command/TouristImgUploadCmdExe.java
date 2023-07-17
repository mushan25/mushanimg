package com.hzb.file.executor.command;

import com.hzb.base.core.annotation.AccessModeAnnotation;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgRemoveCmd;
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
 * @Date: 2023/5/5
 */
@Component
@Slf4j
@AccessModeAnnotation(taxType = AccessMode.TOURIST)
public class TouristImgUploadCmdExe extends ImageStrategy {
    @Value("${minio.bucket.files}")
    private String bucketName;
    private final DomainService domainService;
    public TouristImgUploadCmdExe(ImageGateway imageGateway, RedissonClient redissonClient, DomainService domainService, RocketMqProducer rocketMqProducer) {
        super(imageGateway, redissonClient, rocketMqProducer);
        this.domainService = domainService;
    }

    @Override
    public AjaxResult execute(MultipartFile img) {
        if (img.getSize() > Constants.TOURIST_UPLOAD_SIZE) {
            return AjaxResult.error("图片大小超过限制");
        }

        File tempFile = FileUtils.transferFile(img, tempFilePath);
        Image image = DomainFactory.initImage(bucketName ,img.getOriginalFilename(), img.getSize(), tempFile.getAbsolutePath());
        image.setObjectName(image.initObjectName(Constants.TOURIST_OBJECT_NAME));
        return uploadImage(image, tempFile);
    }

    @Override
    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd) {
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), null)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
