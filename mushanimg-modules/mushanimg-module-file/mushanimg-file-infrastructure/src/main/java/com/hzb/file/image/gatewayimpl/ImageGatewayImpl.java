package com.hzb.file.image.gatewayimpl;

import com.alibaba.cola.exception.SysException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.file.convertor.ImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.image.gatewayimpl.database.ImageMapper;
import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Service实现
* @createDate 2023-05-05 14:34:45
*/
@RequiredArgsConstructor
@Service
@Slf4j
public class ImageGatewayImpl extends ServiceImpl<ImageMapper, ImageDO>
    implements ImageGateway {

    private final MinioClient minioClient;
    private final ImageMapper imageMapper;
    @Value("${minio.bucket.files}")
    private String bucketImg;

    @Override
    public Image upload2Minio(Image image) {
        try {
            String bucketName = image.getBucketName();
            // 1.判断bucket是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }else {
                log.debug("Bucket :{} already exists.", bucketName);
            }
            UploadObjectArgs bucket = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .filename(image.getLocalFilePath())
                    .object(image.getObjectName())
                    .contentType(image.getMimeType())
                    .build();
            ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(bucket);
            image.setImgurl(image.initAccessImgurl(String.format("%s/%s", objectWriteResponse.bucket(), objectWriteResponse.object())))
                    .setVersionId(objectWriteResponse.versionId());
            log.debug("上传文件到minio成功,bucket:{}, object:{}, versionId:{}", bucket, image.getObjectName(), objectWriteResponse.versionId());
            return image;
        } catch (Exception e) {
            log.error("上传文件出错,objectName:{}, info:{}", image.getObjectName(), e.getMessage());
            throw new SysException("上传文件出错");
        }
    }

    @Override
    public Image addImg2Db(Image image) {
        ImageDO imageDO = ImageConvertor.INSTANCT.image2DO(image);
        if(imageMapper.insert(imageDO) > 0){
            return ImageConvertor.INSTANCT.DO2Image(imageDO)
                    .setBucketName(image.getBucketName())
                    .setMimeType(image.getMimeType());
        }
        return null;
    }

    @Override
    public Image selectImgByMd5(Image image) {
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ImageDO::getId, ImageDO::getImgurl)
                .eq(ImageDO::getMd5Key, image.getMd5Key())
                .eq(null != image.getUserId(), ImageDO::getUserId, image.getUserId())
                .isNull(null == image.getUserId(), ImageDO::getUserId);
        return ImageConvertor.INSTANCT.DO2Image(imageMapper.selectOne(wrapper));
    }

    @Override
    public List<Image> getImgList(Image image, List<Long> imgIds) {
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ImageDO::getId, ImageDO::getImgurl)
                .in(null != imgIds && imgIds.size() > 0, ImageDO::getId, imgIds)
                .eq(ImageDO::getUserId, image.getUserId())
                .eq(StringUtils.isNotEmpty(image.getImgType()), ImageDO::getImgType, image.getImgType())
                .eq(StringUtils.isNotEmpty(image.getImgName()), ImageDO::getImgName, image.getImgName());
        return ImageConvertor.INSTANCT.imageDOs2imageList(imageMapper.selectList(wrapper));
    }

    @Override
    public Image getImgInfo(Image image) {
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ImageDO::getId, ImageDO::getImgName, ImageDO::getImgurl, ImageDO::getSize, ImageDO::getImgType,
                ImageDO::getRemark, ImageDO::getCreateTime)
                .eq(ImageDO::getId, image.getId())
                .eq(ImageDO::getUserId, image.getUserId());
        return ImageConvertor.INSTANCT.DO2Image(imageMapper.selectOne(wrapper));
    }

    @Override
    public boolean updateImgInfo(Image image) {
        return new LambdaUpdateChainWrapper<>(imageMapper)
                .set(ImageDO::getImgName, image.getImgName())
                .set(ImageDO::getRemark, image.getRemark())
                .eq(ImageDO::getId, image.getId())
                .eq(ImageDO::getUserId, image.getUserId())
                .update();
    }

    @Override
    public boolean deleteImgDb(List<Long> imgIds, Long userId) {
        if (null == imgIds || imgIds.size() == 0) {
            return false;
        }
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ImageDO::getId, imgIds)
                .isNull(null == userId, ImageDO::getUserId)
                .eq(null != userId,ImageDO::getUserId, userId);
        return imageMapper.delete(wrapper) > 0;
    }

    @Override
    public List<String> deleteImgMinio(List<String> objectNameList) {
        try {
            List<DeleteObject> objects = objectNameList.stream().map(DeleteObject::new).toList();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs
                    .builder().bucket(bucketImg).objects(objects).build());

            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                objectNameList.remove(error.objectName());
                log.error("删除minio文件出错,objectName:{}, message{}", error.objectName(), error.message());
            }
            return objectNameList;
        } catch (Exception e) {
            log.error("删除minio文件出错,objectName:{}, message{}", objectNameList, e.getMessage());
            throw new SysException("删除minio文件出错");
        }
    }

    @Override
    public List<Image> selectObjetNameByIds(List<Long> imgIds, Long userId) {
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ImageDO::getId, ImageDO::getObjectName, ImageDO::getVersionId)
                .isNull(null == userId, ImageDO::getUserId)
                .eq(null != userId,ImageDO::getUserId, userId)
                .in(ImageDO::getId, imgIds);
        return ImageConvertor.INSTANCT.imageDOs2imageList(imageMapper.selectList(wrapper));
    }

    @Override
    public boolean moveImg2OtherClass(Long imgDataId, Long imageclassId) {
        return imageMapper.updateImgClass(imgDataId, imageclassId) > 0;
    }

    @Override
    public boolean deleteImgByImgIds(List<Long> imgIds) {
        return imageMapper.deleteImgByimgIds(imgIds) > 0;
    }

    @Override
    public Long getUserUsedSize(Long userId) {
        QueryWrapper<ImageDO> wrapper = new QueryWrapper<>();
        wrapper.select( "ifnull(sum(size),0) as size")
                .eq("user_id", userId);
        return imageMapper.selectOne(wrapper).getSize();
    }

    @Override
    public void restoreImageByVersionId(List<Image> images) {
        images.parallelStream().forEach(image -> {
            try {
                String bucketName = image.getBucketName();
                minioClient.copyObject(CopyObjectArgs.builder()
                        .bucket(bucketName)
                        .object(image.getObjectName())
                        .source(CopySource.builder()
                                .bucket(bucketName)
                                .object(image.getObjectName())
                                .versionId(image.getVersionId())
                                .build())
                        .build());
            } catch (Exception e) {
                log.error("还原文件出错,image:{}", image);
                throw new SysException("还原文件出错");
            }
        });
    }
}




