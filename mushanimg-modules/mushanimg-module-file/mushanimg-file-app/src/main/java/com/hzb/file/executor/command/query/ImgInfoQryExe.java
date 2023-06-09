package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.dto.ImgInfoQry;
import com.hzb.file.dto.clientobject.ImageCO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/2
 */
@AllArgsConstructor
@Component
public class ImgInfoQryExe {
    private final ImageGateway imageGateway;

    public SingleResponse<ImageCO> execute(ImgInfoQry imgInfoQry){
        return SingleResponse.of(
                AppImageConvertor.INSTANCT.image2CO(
                        imageGateway.getImgInfo(AppImageConvertor.INSTANCT.qry2Image(imgInfoQry))
                )
        );
    }
}
