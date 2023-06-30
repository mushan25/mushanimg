package com.hzb.file.domain.image.model.entities;

import com.alibaba.cola.domain.Entity;
import com.hzb.base.core.constant.Constants;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Data
@Entity
@Slf4j
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    /**
     * 主键
     */
    private Long id;

    /**
     * 图片名
     */
    private String imgName;

    /**
     * 图片链接
     */
    private String imgurl;

    /**
     * 图片版本
     */
    private String versionId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * img类型
     */
    private String imgType;

    /**
     * 文件的mimetype
     */
    private String mimeType;

    /**
     * 图片md5值
     */
    private String md5Key;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片本地地址
     */
    private String localFilePath;

    /**
     * objectName
     */
    private String objectName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public String initMd5Key() {
        try (FileInputStream fileInputStream = new FileInputStream(localFilePath)) {
            return DigestUtils.md5DigestAsHex(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String initMimeType() {
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(getExtension());
        if (Objects.nonNull(extensionMatch)) {
            return extensionMatch.getMimeType();
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    public String initImgType() {
        return imgName.substring(imgName.lastIndexOf(".") + 1);
    }

    public String getExtension() {
        return imgName.substring(imgName.lastIndexOf("."));
    }

    public String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/") + "/";
    }

    public String initObjectName(String nickName) {
        return nickName + "/" + getDefaultFolderPath() + md5Key + getExtension();
    }

    public String initAccessImgurl(String bucketName) {
        return Constants.MINIO_URL + bucketName;
    }
}
