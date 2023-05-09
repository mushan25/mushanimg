package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.domain.CustomPageInfo;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.utils.BeanCopyUtil;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.clientobject.ImageCO;
import org.springframework.beans.BeanUtils;
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
        if (null == imgListQry.getUserId()){
            throw new ServiceException();
        }
        Image image = DomainFactory.getImage();
        BeanUtils.copyProperties(imgListQry, image);
        List<Image> imgList = imageGateway.getImgList(image);
        List<ImageCO> imageCOS = BeanCopyUtil.copyListProperties(imgList, ImageCO::new);
        CustomPageInfo pageInfo = PageUtils.getPageInfo(imageCOS);
        return PageResponse.of(imageCOS, pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
    }
}
