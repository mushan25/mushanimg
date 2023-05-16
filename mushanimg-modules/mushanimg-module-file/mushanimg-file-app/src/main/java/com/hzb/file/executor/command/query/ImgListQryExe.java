package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.domain.CustomPageInfo;
import com.hzb.base.core.utils.CheckUtils;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.clientobject.ImageCO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@Component
public class ImgListQryExe {
    private final ImageGateway imageGateway;

    public ImgListQryExe(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public PageResponse<ImageCO> execute(ImgListQry imgListQry){
        CheckUtils.isTrue(null == imgListQry.getUserId(),null, null);
        List<ImageCO> imageCOS = AppImageConvertor.INSTANCT.imageCOs2ImageCOList(imageGateway.getImgList(AppImageConvertor.INSTANCT.qry2Image(imgListQry)));
        CustomPageInfo pageInfo = PageUtils.getPageInfo(imageCOS);
        return PageResponse.of(imageCOS, pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
    }
}
