package com.hzb.file.executor.service;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgRemoveCmd;
import com.hzb.file.executor.service.mq.RocketMqProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: hzb
 * @Date: 2023/7/3
 */
@RequiredArgsConstructor
@Slf4j
public abstract class ImageStrategy {
    @Value("${mushanimg.tempfile-path}")
    protected String tempFilePath;
    @Value("${mushanimg.review.topic}")
    protected String reviewTopic;
    protected final ImageGateway imageGateway;
    protected final RedissonClient redissonClient;
    protected final RocketMqProducer rocketMqProducer;

    /**
     * 上传图片
     *
     * @param img 图片
     * @return 上传结果
     */
    public abstract AjaxResult execute(MultipartFile img);

    /**
     * 删除图片
     *
     * @param imgRemoveCmd 图片信息
     * @return 删除结果
     */
    public abstract AjaxResult execute(ImgRemoveCmd imgRemoveCmd);

    /**
     * 上传图片
     *
     * @param image    图片
     * @param tempFile 临时文件
     */
    public AjaxResult uploadImage(Image image, File tempFile) {
        RLock lock;
        Long userId = Optional.ofNullable(image).orElseThrow().getUserId();
        if (Objects.isNull(userId)) {
            lock = redissonClient.getLock(image.getMd5Key());
        } else {
            lock = redissonClient.getLock(userId + ":" + image.getMd5Key());
        }

        try {
            lock.tryLock(Constants.REDISSON_LOCK_WAIT_TIME, Constants.REDISSON_LOCK_LEASE_TIME, TimeUnit.SECONDS);
            Image existImage = imageGateway.selectImgByMd5(image);
            if (Objects.nonNull(existImage)) {
                return AjaxResult.success(AppImageConvertor.INSTANCT.image2ImageListCO(existImage));
            }
            Image uploadResult = imageGateway.addImg2Db(imageGateway.upload2Minio(image));
            if (Objects.nonNull(uploadResult)) {
                rocketMqProducer.asyncSendMessage(uploadResult, reviewTopic);
                return AjaxResult.success(AppImageConvertor.INSTANCT.image2ImageListCO(uploadResult));
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
}
