package com.hzb.file.domain.ability;

import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: hzb
 * @Date: 2023/4/19
 */
@Service
public class DomainService {
    private final ImageGateway imageGateway;
    private final ImageclassGateway imageclassGateway;


    public DomainService(ImageGateway imageGateway, ImageclassGateway imageclassGateway) {
        this.imageGateway = imageGateway;
        this.imageclassGateway = imageclassGateway;
    }

    public boolean deleteImg(List<Long> imgIds, Long userId) {
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

    public boolean deleteImacgeclass(List<Long> imageclassIds, Long userId) {
        return false;
    }

    public boolean moveImageclass(Image image, Imageclass imageclass) {
        if(imageclassGateway.checkImageclassExist(imageclass)){
            return imageGateway.moveImg2OtherClass(image.getId(), imageclass.getId());
        }
        return false;
    }
}
