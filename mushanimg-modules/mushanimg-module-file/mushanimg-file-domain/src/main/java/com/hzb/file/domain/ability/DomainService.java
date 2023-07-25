package com.hzb.file.domain.ability;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import com.hzb.base.core.utils.CheckUtils;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@AllArgsConstructor
@Service
public class DomainService {
    private final ImageGateway imageGateway;
    private final ImageclassGateway imageclassGateway;

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public boolean deleteImg(List<Long> imgIds, Long userId) {
        CheckUtils.isPresentRunnable(userId).presentHandler(() -> {
            imageGateway.deleteImgByImgIds(imgIds);
        });
        List<Image> images = imageGateway.selectObjetNameByIds(imgIds, userId);
        if (null == images || images.size() == 0) {
            return false;
        }
        List<String> objectNameList = images.stream().map(Image::getObjectName).toList();

        List<String> removeObjectNameList = imageGateway.deleteImgMinio(objectNameList);

        List<Long> removeImageIds = images.stream().map(image -> {
            if (removeObjectNameList.contains(image.getObjectName())) {
                return image.getId();
            }
            return null;
        }).toList();
        try {
            return imageGateway.deleteImgDb(removeImageIds, userId);
        }catch (RuntimeException e){
            List<Image> versionIds = images.stream().map(image -> {
                if (removeImageIds.contains(image.getId())) {
                    return image;
                }
                return null;
            }).toList();
            imageGateway.restoreImageByVersionId(versionIds);
            throw new SysException("删除图片失败");
        }
    }

    public List<Image> getImgList(Image image, Long imageclassId) {
        if(Objects.isNull(imageclassId)) {
            return imageGateway.getImgList(image, null);
        }

        List<Long> imgIds = imageclassGateway.getImgIdsByImageclassId(imageclassId);
        if (null != imgIds && imgIds.size() > 0) {
            return imageGateway.getImgList(image, imgIds);
        }
        return null;
    }

    public boolean moveImageclass(List<Long> imgIds, Imageclass imageclass) {
        if (Objects.nonNull(imageclass.getId()) && !imageclassGateway.checkImageclassExist(imageclass)) {
            throw new BizException("图片分类不存在");
        }
        return imageGateway.moveImg2OtherClass(imgIds, imageclass.getId());
    }
}
