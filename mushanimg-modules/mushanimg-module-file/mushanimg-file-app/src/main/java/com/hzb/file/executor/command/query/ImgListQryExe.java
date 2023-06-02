package com.hzb.file.executor.command.query;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.clientobject.ImageListCO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/8
 */
@Component
@Slf4j
public class ImgListQryExe {
    private final DomainService domainService;

    public ImgListQryExe(DomainService domainService) {
        this.domainService = domainService;
    }

    public PageResponse<ImageListCO> execute(ImgListQry imgListQry){
        Image image = AppImageConvertor.INSTANCT.qry2Image(imgListQry);
        List<Image> imgList = domainService.getImgList(image, imgListQry.getImgclassIds());
        List<ImageListCO> imageListCOS = AppImageConvertor.INSTANCT.imageList2ImageListCOList(imgList);
        return PageUtils.getPageResponse(imageListCOS);
    }
}
