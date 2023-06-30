package com.hzb.file.imageclass.gatewayimpl.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;

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

    /**
     * 根据图片id删除图片分类
     * @param imgIds 图片id列表
     * @return 删除结果
     */
    int deleteImageclassByImgIds(List<Long> imgIds);

    /**
     * 根据图片分类id删除图片
     * @param imageclassIds 图片分类id列表
     * @return 删除结果
     */
    int deleteImgByImageclassId(List<Long> imageclassIds);
    /**
     * 根据图片分类id查询图片分类是否存在
     * @param imageclassIds 图片分类id列表
     * @return 查询结果
     */

    int checkImageExist(List<Long> imageclassIds);
}




