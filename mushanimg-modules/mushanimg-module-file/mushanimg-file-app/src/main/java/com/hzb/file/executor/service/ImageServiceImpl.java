package com.hzb.file.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.executor.command.ImgUploadCmdExe;
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

    public ImageServiceImpl(ImgUploadCmdExe imgUploadCmdExe, ImgListQryExe imgListQryExe) {
        this.imgUploadCmdExe = imgUploadCmdExe;
        this.imgListQryExe = imgListQryExe;
    }

    @Override
    public AjaxResult uploadImg(ImgUploadCmd imgUploadCmd) {
        return imgUploadCmdExe.execute(imgUploadCmd);
    }

    @Override
    public PageResponse<ImageCO> getImageList(ImgListQry imgListQry) {
        return imgListQryExe.execute(imgListQry);
    }
}
