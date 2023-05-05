package com.hzb.file.image.gatewayimpl;

import com.alibaba.cola.exception.SysException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.file.convertor.ImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.image.gatewayimpl.database.ImageMapper;
import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import com.hzb.file.domain.image.model.entities.Image;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Service实现
* @createDate 2023-05-05 14:34:45
*/
@Service
@Slf4j
public class ImageGatewayImpl extends ServiceImpl<ImageMapper, ImageDO>
    implements ImageGateway {

    private final MinioClient minioClient;
    private final ImageMapper imageMapper;
    @Value("${minio.bucket.files}")
    private String bucket_img;

    public ImageGatewayImpl(MinioClient minioClient, ImageMapper imageMapper) {
        this.minioClient = minioClient;
        this.imageMapper = imageMapper;
    }

    @Override
    public boolean upload2Minio(Image image) {
        String objectName = image.getDefaultFolderPath() + image.getMd5Key() + image.getExtension();
        try {
            // 1.判断bucket是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket_img).build());
            if (!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket_img).build());
            }else {
                log.debug("Bucket :{} already exists.", bucket_img);
            }
            UploadObjectArgs bucket = UploadObjectArgs.builder()
                    .bucket(bucket_img)
                    .filename(image.getLocalFilePath())
                    .object(objectName)
                    .contentType(image.getMimeType())
                    .build();
            minioClient.uploadObject(bucket);
            log.debug("上传文件到minio成功,bucket:{} object:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{}", bucket_img, objectName);
            throw new SysException("上传文件出错");
        }
    }

    @Override
    public boolean addImg2Db(Image image) {
        ImageDO imageDO = ImageConvertor.toDataObjectCreate(image);
        return imageMapper.insert(imageDO) > 0;
    }

    @Override
    public boolean selectImg(String imgMd5Key) {


        return false;
    }
}




