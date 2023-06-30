package com.hzb.file.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.ImageService;
import com.hzb.file.api.ImageStrategy;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.dto.clientobject.ImageListCO;
import com.hzb.file.executor.command.ImgInfoEditCmdExe;
import com.hzb.file.executor.command.ImgMoveClassCmdExe;
import com.hzb.file.executor.command.query.ImgInfoQryExe;
import com.hzb.file.executor.command.query.ImgListQryExe;
import com.hzb.file.factory.AnnotationAccessStrategyFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImgListQryExe imgListQryExe;
    private final ImgInfoQryExe imgInfoQryExe;
    private final ImgInfoEditCmdExe imgInfoEditCmdExe;
    private final ImgMoveClassCmdExe imgMoveClassCmdExe;

    @Override
    public AjaxResult uploadImg(MultipartFile img) {
        ImageStrategy accessStrategy = AnnotationAccessStrategyFactory.getAccessStrategy(SecurityUtils.checkAccessMode());
        return accessStrategy.execute(img);
    }

    @Override
    public PageResponse<ImageListCO> getImageList(ImgListQry imgListQry) {
        return imgListQryExe.execute(imgListQry);
    }

    @Override
    public SingleResponse<ImageCO> getImageInfo(ImgInfoQry imgInfoQry) {
        return imgInfoQryExe.execute(imgInfoQry);
    }

    @Override
    public AjaxResult editImageInfo(ImgInfoEditCmd imgInfoEditCmd) {
        return imgInfoEditCmdExe.execute(imgInfoEditCmd);
    }

    @Override
    public AjaxResult removeImage(ImgRemoveCmd imgRemoveCmd) {
        ImageStrategy accessStrategy = AnnotationAccessStrategyFactory.getAccessStrategy(SecurityUtils.checkAccessMode());
        return accessStrategy.execute(imgRemoveCmd);
    }

    @Override
    public AjaxResult moveImage2OtherClass(ImgMoveClassCmd imgMoveClassCmd) {
        return imgMoveClassCmdExe.execute(imgMoveClassCmd);
    }
}
