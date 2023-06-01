package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.DomainFactory;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.dto.ImgUploadCmd;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@Component
public class ImgUploadCmdExe {
    private final ImageGateway imageGateway;

    public ImgUploadCmdExe(ImageGateway imageGateway) {
        this.imageGateway = imageGateway;
    }

    public AjaxResult execute(ImgUploadCmd imgUploadCmd){
        Image image = DomainFactory.getImage();
        image = AppImageConvertor.INSTANCT.cmd2Image(imgUploadCmd);
        image.setImgType();
        image.setMimeType();
        image.setMd5Key();
        image.setObjectName();
        if (imageGateway.selectImgByMd5(image.getMd5Key())){
            return AjaxResult.success();
        }
        if (imageGateway.upload2Minio(image) && imageGateway.addImg2Db(image)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
