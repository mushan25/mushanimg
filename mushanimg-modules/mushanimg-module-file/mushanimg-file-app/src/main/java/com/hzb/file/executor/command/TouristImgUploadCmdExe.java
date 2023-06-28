package com.hzb.file.executor.command;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.core.annotation.AccessModeAnnotation;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageStrategy;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgRemoveCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@RequiredArgsConstructor
@Component
@Slf4j
@AccessModeAnnotation(taxType = AccessMode.TOURIST)
public class TouristImgUploadCmdExe implements ImageStrategy {
    @Value("${mushanimg.tempfile-path}")
    private String tempFilePath;
    @Value("${mushanimg.review.topic}")
    private String reviewTopic;
    private final ImageGateway imageGateway;
    private final DomainService domainService;
    private final RocketMQTemplate rocketMQTemplate;
    private final RedissonClient redissonClient;

    @Override
    public AjaxResult execute(MultipartFile[] imgs) {
        FileUtils.set(tempFilePath);
        List<String> imgUrlList = Arrays.stream(imgs).collect(Collectors.toSet()).stream()
                .limit(Constants.TOURIST_UPLOAD_COUNT)
                .parallel()
                .map(img -> {
                    Image image = DomainFactory.getImage();
                    File tempFile;
                    try {
                        if (img.getSize() > Constants.TOURIST_UPLOAD_SIZE) {
                            return null;
                        }
                        tempFile = File.createTempFile("minio", "temp", FileUtils.get());
                        img.transferTo(tempFile);
                        image.assembleImage(null, img, tempFile);
                        image.initObjectName(Constants.TOURIST_OBJECT_NAME);
                    }catch (Exception e) {
                        log.info("图片初始化失败:{}", e.getMessage());
                        throw new SysException("图片上传失败");
                    }
                    RLock lock = redissonClient.getLock(image.getMd5Key());
                    try {
                        lock.tryLock(Constants.REDISSON_LOCK_WAIT_TIME, Constants.REDISSON_LOCK_LEASE_TIME, TimeUnit.SECONDS);
                        Image existImage = imageGateway.selectImgByMd5(image);
                        if (Objects.nonNull(existImage)) {
                            return existImage.getImgurl();
                        }
                        Image uploadResult = imageGateway.upload2Minio(image);
                        if (imageGateway.addImg2Db(uploadResult)) {
                            asyncSendMessage(image, rocketMQTemplate, reviewTopic, log);
                            return image.getImgurl();
                        }
                        return null;
                    } catch (Exception e) {
                        log.info("图片上传失败:{}", e.getMessage());
                        imageGateway.deleteImgMinio(Collections.singletonList(image.getObjectName()));
                        throw new SysException("图片上传失败");
                    } finally {
                        if (lock.isLocked() && lock.isHeldByCurrentThread()){
                            lock.unlock();
                        }
                        FileUtils.deleteTempFile(tempFile);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        FileUtils.remove();
        return AjaxResult.success(imgUrlList);
    }

    @Override
    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd){
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), null)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
