package com.hzb.file.imageclass.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.file.convertor.ImageclassConvertor;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.imageclass.gatewayimpl.database.ImageclassMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgclass(图片分类表)】的数据库操作Service实现
* @createDate 2023-06-01 17:22:33
*/
@AllArgsConstructor
@Service
public class ImageclassGatewayImpl extends ServiceImpl<ImageclassMapper, ImageclassDO>
    implements ImageclassGateway {

    private final ImageclassMapper imageclassMapper;

    @Override
    public List<Imageclass> getImageclassList(Long userId) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, userId);
        return ImageclassConvertor.INSTANCT.DOs2Imageclasses(imageclassMapper.selectList(wrapper));
    }

    @Override
    public Imageclass getImageclssInfo(Imageclass imageclass) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());
        return ImageclassConvertor.INSTANCT.DO2Imageclass(imageclassMapper.selectOne(wrapper));
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
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());
        return imageclassMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<Long> getImgIdsByImageclassId(List<Long> imageclassIds) {
        return imageclassMapper.selectImgIdsByImageclassId(imageclassIds);
    }

    @Override
    public boolean deleteImageclassByImgIds(List<Long> imgIds) {
        return false;
    }
}




