package com.hzb.file.image.gatewayimpl;

import com.alibaba.cola.exception.SysException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Service实现
* @createDate 2023-05-05 14:34:45
*/
@AllArgsConstructor
@Service
@Slf4j
public class ImageGatewayImpl extends ServiceImpl<ImageMapper, ImageDO>
    implements ImageGateway {

    private final MinioClient minioClient;
    private final ImageMapper imageMapper;
    @Value("${minio.bucket.files}")
    private String bucket_img;

    @Override
    public boolean upload2Minio(Image image) {
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
                    .object(image.getObjectName())
                    .contentType(image.getMimeType())
                    .build();
            minioClient.uploadObject(bucket);
            log.debug("上传文件到minio成功,bucket:{} object:{}", bucket, image.getObjectName());
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{}", bucket_img, image.getObjectName());
            throw new SysException("上传文件出错");
        }
    }

    @Override
    public boolean addImg2Db(Image image) {
        image.setImgurl2(bucket_img);
        return imageMapper.insert(ImageConvertor.INSTANCT.image2DO(image)) > 0;
    }

    @Override
    public boolean selectImgByMd5(String imgMd5Key) {
        LambdaQueryWrapper<ImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(imgMd5Key), ImageDO::getMd5Key, imgMd5Key);
        return imageMapper.selectCount(wrapper) > 0;
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
                .eq(ImageDO::getUserId, userId);
        return imageMapper.delete(wrapper) > 0;
    }

    @Override
    public List<String> deleteImgMinio(List<String> objectNameList) {
        try {
            List<DeleteObject> objects = objectNameList.stream().map(DeleteObject::new).toList();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs
                    .builder().bucket(bucket_img).objects(objects).build());

            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                objectNameList.remove(error.objectName());
                // TODO 删除失败则重试
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
        wrapper.select(ImageDO::getId, ImageDO::getObjectName)
                .eq(ImageDO::getUserId, userId)
                .in(ImageDO::getId, imgIds);
        return ImageConvertor.INSTANCT.imageDOs2imageList(imageMapper.selectList(wrapper));
    }

    @Override
    public boolean moveImg2OtherClass(Long imgDataId, Long imageclassId) {
        return imageMapper.updateImgClass(imgDataId, imageclassId) > 0;
    }
}




