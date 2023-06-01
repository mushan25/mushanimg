package com.hzb.file.imageclass.gatewayimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.imageclass.gatewayimpl.database.ImageclassMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgclass(图片分类表)】的数据库操作Service实现
* @createDate 2023-06-01 17:22:33
*/
@Service
public class ImageclassGatewayImpl extends ServiceImpl<ImageclassMapper, ImageclassDO>
    implements ImageclassGateway {

    @Override
    public List<Imageclass> getImageclassList(Long userId) {
        return null;
    }

    @Override
    public Imageclass getImageclssInfo(Imageclass imageclass) {
        return null;
    }

    @Override
    public boolean addImageclass(Imageclass imageclass) {
        return false;
    }

    @Override
    public boolean updateImageclass(Imageclass imageclass) {
        return false;
    }

    @Override
    public boolean deleteImageclass(List<Long> imageclassIds, Long userId) {
        return false;
    }

    @Override
    public boolean checkImageclassExist(Imageclass imageclass) {
        return false;
    }

    @Override
    public List<Long> getImgIdsByImageclassId(List<Long> imageclassIds) {
        return null;
    }

    @Override
    public boolean deleteImageclassByImgIds(List<Long> imgIds) {
        return false;
    }
}




