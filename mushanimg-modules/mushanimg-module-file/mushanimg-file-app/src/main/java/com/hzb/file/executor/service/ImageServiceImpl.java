package com.hzb.file.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.dto.clientobject.ImageListCO;
import com.hzb.file.executor.command.ImgUploadCmdExe;
import com.hzb.file.executor.command.query.ImgInfoQryExe;
import com.hzb.file.executor.command.query.ImgListQryExe;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final ImgUploadCmdExe imgUploadCmdExe;
    private final ImgListQryExe imgListQryExe;
    private final ImgInfoQryExe imgInfoQryExe;

    public ImageServiceImpl(ImgUploadCmdExe imgUploadCmdExe, ImgListQryExe imgListQryExe, ImgInfoQryExe imgInfoQryExe) {
        this.imgUploadCmdExe = imgUploadCmdExe;
        this.imgListQryExe = imgListQryExe;
        this.imgInfoQryExe = imgInfoQryExe;
    }

    @Override
    public AjaxResult uploadImg(ImgUploadCmd imgUploadCmd) {
        return imgUploadCmdExe.execute(imgUploadCmd);
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
        return null;
    }

    @Override
    public AjaxResult removeImage(ImgRemoveCmd imgRemoveCmd) {
        return null;
    }

    @Override
    public AjaxResult moveImage2OtherClass(ImgMoveClassCmd imgMoveClassCmd) {
        return null;
    }
}
