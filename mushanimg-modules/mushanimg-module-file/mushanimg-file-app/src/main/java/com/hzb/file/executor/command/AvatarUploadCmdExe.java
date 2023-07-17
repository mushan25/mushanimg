package com.hzb.file.executor.command;

import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.utils.FileUtils;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.form.LoginUser;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.executor.service.mq.RocketMqProducer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * @author: hzb
 * @Date: 2023/7/17
 */
@RequiredArgsConstructor
@Component
public class AvatarUploadCmdExe {
    private final ImageGateway imageGateway;
    private final RocketMqProducer rocketMqProducer;
    @Value("${minio.bucket.avatar}")
    private String bucketName;
    @Value("${mushanimg.tempfile-path}")
    protected String tempFilePath;
    @Value("${mushanimg.review.topic}")
    private String reviewTopic;

    public AjaxResult execute(MultipartFile avatar) {
        if (avatar.getSize() > Constants.AVATAR_SIZE) {
            return AjaxResult.error("头像大小不能超过5M");
        }
        LoginUser user = SecurityUtils.getUser();

        File tempFile = FileUtils.transferFile(avatar, tempFilePath);
        Image image = DomainFactory.getImage();

        image.setImgName(avatar.getOriginalFilename())
                .setObjectName(image.intiAvatarObjectName(user.getUserId(), user.getUser().getNickName()))
                .setBucketName(bucketName)
                .setLocalFilePath(tempFile.getAbsolutePath())
                .setMimeType(image.initMimeType());
        if (Objects.nonNull(imageGateway.upload2Minio(image))) {
            rocketMqProducer.asyncSendMessage(image, reviewTopic);
            return AjaxResult.success("上传头像成功");
        }
        return AjaxResult.error("上传头像失败");
    }
}
