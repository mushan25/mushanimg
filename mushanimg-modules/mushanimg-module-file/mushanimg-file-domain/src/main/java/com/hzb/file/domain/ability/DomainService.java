package com.hzb.file.domain.ability;

import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return false;
    }

    public List<Image> getImgList(Image image, List<Long> imageclassIds) {
        if(null != imageclassIds && imageclassIds.size() > 0){
            List<Long> imgIds = imageclassGateway.getImgIdsByImageclassId(imageclassIds);
            return imageGateway.getImgList(image, imgIds);
        }

        return imageGateway.getImgList(image, null);
    }

    public boolean deleteImacgeclass(List<Long> imageclassIds, Long userId) {
        return false;
    }

    public boolean moveImageclass(Long imageclassId, Long userId) {
        return false;
    }
}
