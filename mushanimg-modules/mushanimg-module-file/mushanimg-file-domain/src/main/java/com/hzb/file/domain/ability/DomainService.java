package com.hzb.file.domain.ability;

import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Service
public class DomainService {
    private final ImageGateway imageGateway;
    private final ImageclassGateway imageclassGateway;
    private final DomainService domainServiceProxy;

    public DomainService(ImageGateway imageGateway, ImageclassGateway imageclassGateway, @Lazy DomainService domainServiceProxy) {
        this.imageGateway = imageGateway;
        this.imageclassGateway = imageclassGateway;
        this.domainServiceProxy = domainServiceProxy;
    }

    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public boolean deleteImg(List<Long> imgIds, Long userId) {
        imageGateway.deleteImgByImgIds(imgIds);
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
        // TODO 删除失败则重试
        return imageGateway.deleteImgDb(removeImageIds, userId);
    }

    public List<Image> getImgList(Image image, List<Long> imageclassIds) {
        if(null == imageclassIds || imageclassIds.size() == 0){
            return imageGateway.getImgList(image, null);
        }

        List<Long> imgIds = imageclassGateway.getImgIdsByImageclassId(imageclassIds);
        if (null != imgIds && imgIds.size() > 0) {
            return imageGateway.getImgList(image, imgIds);
        }
        return null;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteImacgeclass(List<Long> imageclassIds, Long userId) {
        imageclassGateway.deleteImageclass(imageclassIds, userId);
        List<Long> imgIds = imageclassGateway.getImgIdsByImageclassId(imageclassIds);
        if (null == imgIds || imgIds.size() == 0) {
            return true;
        }
        return domainServiceProxy.deleteImg(imgIds, userId);
    }

    public boolean moveImageclass(Image image, Imageclass imageclass) {
        if(imageclassGateway.checkImageclassExist(imageclass)){
            return imageGateway.moveImg2OtherClass(image.getId(), imageclass.getId());
        }
        return false;
    }
}
