package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImageclassInfoQry;
import com.hzb.file.dto.clientobject.ImageclassCO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Component
public class ImageclassInfoQryExe {
    private final ImageclassGateway imageclassGateway;

    public SingleResponse<ImageclassCO> execute(ImageclassInfoQry imageclassInfoQry){
        Imageclass imageclass = imageclassGateway.getImageclssInfo(AppImageclassConvertor.INSTANCT.qry2Imageclass(imageclassInfoQry));
        return SingleResponse.of(AppImageclassConvertor.INSTANCT.imageclass2CO(imageclass));
    }
}
