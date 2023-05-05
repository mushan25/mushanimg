package com.hzb.file.executor.service;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.executor.command.ImgUploadCmdExe;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final ImgUploadCmdExe imgUploadCmdExe;

    public ImageServiceImpl(ImgUploadCmdExe imgUploadCmdExe) {
        this.imgUploadCmdExe = imgUploadCmdExe;
    }

    @Override
    public AjaxResult uploadImg(ImgUploadCmd imgUploadCmd) {
        return imgUploadCmdExe.execute(imgUploadCmd);
    }
}
