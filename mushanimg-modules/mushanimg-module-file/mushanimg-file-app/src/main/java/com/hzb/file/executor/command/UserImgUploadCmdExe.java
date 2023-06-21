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
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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

    @Override
    public AjaxResult execute(MultipartFile[] imgs) {
        Long useId;
        try {
            useId = SecurityUtils.getUserId();
        } catch (Exception e){
            throw new ServiceException("用户未登录");
        }
        FileUtils.set(tempFilePath);
        UploadUserInfo userInfo = userClientService.getUserInfo(useId);
        long totalSize = userInfo.getUploadSize() * Constants.MB_SIZE;
        AtomicLong count = new AtomicLong(totalSize - imageGateway.getUserUsedSize(useId));
        List<String> imgUrlList = Arrays.stream(imgs).limit(10).parallel()
                .map(img -> {
                    Image image = DomainFactory.getImage();
                    File tempFile = null;
                    try {
                        if (img.getSize() > count.get()){
                            throw new ServiceException("存储空间不足");
                        }
                        if (img.getSize() > Constants.USER_UPLOAD_SIZE) {
                            return null;
                        }
                        tempFile = File.createTempFile("minio", "temp", FileUtils.get());
                        img.transferTo(tempFile);
                        image.assembleImage(useId, img, tempFile);
                        image.initObjectName(userInfo.getUserName());
                        Image existImage = imageGateway.selectImgByMd5(image);
                        if (null != existImage) {
                            return existImage.getImgurl();
                        }
                        Image uploadResult = imageGateway.upload2Minio(image);
                        if (imageGateway.addImg2Db(uploadResult)) {
                            count.addAndGet(-img.getSize());
                            asyncSendMessage(image, rocketMQTemplate, reviewTopic, log);
                            return image.getImgurl();
                        }
                        return null;
                    } catch (Exception e) {
                        log.info("图片上传失败:{}", e.getMessage());
                        imageGateway.deleteImgMinio(Collections.singletonList(image.getObjectName()));
                        throw new SysException("图片上传失败");
                    } finally {
                        FileUtils.deleteTempFile(tempFile);
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
        FileUtils.remove();
        return AjaxResult.success(imgUrlList);
    }

    @Override
    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd){
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), SecurityUtils.getUserId())){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
