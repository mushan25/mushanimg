package com.hzb.file.domain.image.model.entities;

import com.alibaba.cola.domain.Entity;
import com.hzb.base.core.constant.Constants;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Data
@Entity
@Slf4j
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

    public void setMd5Key() {
        try( FileInputStream fileInputStream = new FileInputStream(localFilePath)) {
            md5Key =  DigestUtils.md5DigestAsHex(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMimeType() {
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(getExtension());
        mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (null != extensionMatch){
            mimeType = extensionMatch.getMimeType();
        }
    }

    public void setImgType() {
        imgType = imgName.substring(imgName.lastIndexOf(".") + 1);
    }

    public String getExtension(){
        return imgName.substring(imgName.lastIndexOf("."));
    }

    public String getDefaultFolderPath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/") + "/";
    }

    public void setObjectName() {
        objectName = getDefaultFolderPath() + md5Key + getExtension();
    }

    public void setImgurl2(String bucketName) {
        imgurl = Constants.MINIO_URL + bucketName + "/" + getObjectName();
    }
}
