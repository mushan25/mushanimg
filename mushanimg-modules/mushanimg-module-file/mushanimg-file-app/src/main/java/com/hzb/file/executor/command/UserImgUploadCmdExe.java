package com.hzb.file.executor.command;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.core.annotation.AccessModeAnnotation;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.ImageStrategy;
import com.hzb.file.api.UserClientService;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgRemoveCmd;
import com.hzb.file.dto.grpc.UploadUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
@RequiredArgsConstructor
@Component
@Slf4j
@AccessModeAnnotation(taxType = AccessMode.NORM_USER)
public class UserImgUploadCmdExe implements ImageStrategy {
    @Value("${mushanimg.tempfile-path}")
    private String tempFilePath;
    @Value("${mushanimg.review.topic}")
    private String reviewTopic;
    private final ImageGateway imageGateway;
    private final UserClientService userClientService;
    private final DomainService domainService;
    private final RocketMQTemplate rocketMQTemplate;
    private final RedissonClient redissonClient;

    @Override
    public AjaxResult execute(MultipartFile img) {
        Long useId = SecurityUtils.getUserId();
        if (img.getSize() > Constants.USER_UPLOAD_SIZE) {
            return AjaxResult.error("图片大小超过限制");
        }

        File tempFile = FileUtils.transferFile(img, tempFilePath);

        UploadUserInfo userInfo = userClientService.getUserInfo(useId);
        long totalSize = userInfo.getUploadSize() * Constants.MB_SIZE;
        long count = totalSize - imageGateway.getUserUsedSize(useId);
        if (img.getSize() > count) {
            throw new ServiceException("存储空间不足");
        }

        Image image = DomainFactory.initImage(img.getOriginalFilename(), img.getSize(), tempFile.getAbsolutePath());
        RLock lock = redissonClient.getLock(useId + ":" + image.getMd5Key());

        try {
            lock.tryLock(Constants.REDISSON_LOCK_WAIT_TIME, Constants.REDISSON_LOCK_LEASE_TIME, TimeUnit.SECONDS);
            Image existImage = imageGateway.selectImgByMd5(image);
            if (null != existImage) {
                return AjaxResult.success(existImage.getImgurl());
            }
            Image uploadResult = imageGateway.upload2Minio(image);
            if (imageGateway.addImg2Db(uploadResult)) {
                asyncSendMessage(image, rocketMQTemplate, reviewTopic, log);
                return AjaxResult.success(image.getImgurl());
            }
            throw new SysException("图片上传失败");
        } catch (Exception e) {
            log.info("图片上传失败:{}", e.getMessage());
            imageGateway.deleteImgMinio(Collections.singletonList(image.getObjectName()));
            throw new SysException("图片上传失败");
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            FileUtils.deleteTempFile(tempFile);
        }
    }

    @Override
    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd) {
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), SecurityUtils.getUserId())) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
