package com.hzb.file.image.gatewayimpl.database;

import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Mapper
* @createDate 2023-05-05 14:34:45
* @Entity com.hzb.file.image.gatewayimpl.database.dataobject.MsImgdata
*/
public interface ImageMapper extends BaseMapper<ImageDO> {

    /**
     * 更新图片分类
     * @param imgDataIds 图片ids
     * @param imageclassId 图片分类id
     * @return update result
     */
    int insertImgClass(@Param("imgDataIds") List<Long> imgDataIds, @Param("imageclassId") Long imageclassId);

    /**
     * 根据图片id获取图片分类id
     * @param imgIds 图片id列表
     * @return 图片分类id列表
     */
    int deleteImgByimgIds(List<Long> imgIds);

    /**
     * 判断图片是否已经分类
     * @param imgIds 图片id列表
     * @return 图片id列表
     */
    List<Long> checkImageHasClass(List<Long> imgIds);

    /**
     * 更新图片分类
     * @param existImgIds 图片ids
     * @param imageclassId 图片分类id
     * @return update result
     */
    int updateImgClass(@Param("existImgIds") List<Long> existImgIds,@Param("imageclassId") Long imageclassId);
}




