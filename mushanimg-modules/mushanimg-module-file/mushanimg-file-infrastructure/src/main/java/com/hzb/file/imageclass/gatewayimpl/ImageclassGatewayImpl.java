package com.hzb.file.imageclass.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.file.convertor.ImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.imageclass.gatewayimpl.database.ImageclassMapper;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
    public Imageclass getImageclssInfo(@NotNull Imageclass imageclass) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());
        return ImageclassConvertor.INSTANCT.DO2Imageclass(imageclassMapper.selectOne(wrapper));
    }

    @Override
    public boolean addImageclass(Imageclass imageclass) {
        return imageclassMapper.insert(ImageclassConvertor.INSTANCT.Imageclass2DO(imageclass)) > 0;
    }

    @Override
    public boolean updateImageclass(@NotNull Imageclass imageclass) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());
        return imageclassMapper.update(ImageclassConvertor.INSTANCT.Imageclass2DO(imageclass), wrapper) > 0;
    }

    @Override
    public boolean deleteImageclass(Long imageclassId, Long userId) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, userId)
                .eq(ImageclassDO::getId, imageclassId);
        return imageclassMapper.delete(wrapper) > 0;
    }

    @Override
    public boolean checkImageclassExist(@NotNull Imageclass imageclass) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());
        return imageclassMapper.exists(wrapper);
    }

    @Override
    public List<Long> getImgIdsByImageclassId(Long imageclassId) {
        return imageclassMapper.selectImgIdsByImageclassId(imageclassId);
    }

    @Override
    public boolean deleteImageclassByImgIds(List<Long> imgIds) {
        return imageclassMapper.deleteImageclassByImgIds(imgIds) > 0;
    }

    @Override
    public boolean deleteImgByImageclassId(List<Long> imageclassIds) {
        return imageclassMapper.deleteImgByImageclassId(imageclassIds) > 0;
    }

    @Override
    public boolean checkImageExist(Long imageclassId) {
        return imageclassMapper.checkImageExist(imageclassId) > 0;
    }

    @Override
    public Long countImageclass(Long userId) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, userId);
        return imageclassMapper.selectCount(wrapper);
    }

    @Override
    public boolean checkImageclassExistByName(Imageclass imageclass) {
        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getImgclassName, imageclass.getImgclassName());
        return imageclassMapper.exists(wrapper);
    }
}




