package com.hzb.file.imageclass.gatewayimpl.database;

import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_imgclass(图片分类表)】的数据库操作Mapper
* @createDate 2023-06-01 17:22:33
* @Entity com.hzb.file.imageclass.gatewayimpl.database.dataobject.MsImgclass
*/
public interface ImageclassMapper extends BaseMapper<ImageclassDO> {

    /**
     * 根据图片分类id获取图片id
     * @param imageclassIds 图片分类id列表
     * @return 图片id列表
     */
    List<Long> selectImgIdsByImageclassId(List<Long> imageclassIds);
}




