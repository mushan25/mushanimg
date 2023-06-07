package com.hzb.file.image.gatewayimpl.database;

import com.hzb.file.image.gatewayimpl.database.dataobject.ImageDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【ms_imgdata】的数据库操作Mapper
* @createDate 2023-05-05 14:34:45
* @Entity com.hzb.file.image.gatewayimpl.database.dataobject.MsImgdata
*/
public interface ImageMapper extends BaseMapper<ImageDO> {

    /**
     * 更新图片分类
     * @param imgDataId 图片id
     * @param imageclassId 图片分类id
     * @return update result
     */
    int updateImgClass(@Param("imgDataId") Long imgDataId, @Param("imageclassId") Long imageclassId);
}




