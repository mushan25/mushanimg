package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImageclassListQry;
import com.hzb.file.dto.clientobject.ImageclassListCO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Component
public class ImageclassListQryExe {
    private final ImageclassGateway imageclassGateway;

    public PageResponse<ImageclassListCO> execute(ImageclassListQry imageclassListQry){

        List<Imageclass> imageclassList = imageclassGateway.getImageclassList(imageclassListQry.getUserId());
        List<ImageclassListCO> imageclassListCOS = AppImageclassConvertor.INSTANCT.imageclassList2CO(imageclassList);
        return PageUtils.getPageResponse(imageclassListCOS);
    }
}
