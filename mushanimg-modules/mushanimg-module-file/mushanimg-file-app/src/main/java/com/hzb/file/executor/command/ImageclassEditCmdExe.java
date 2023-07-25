package com.hzb.file.executor.command;

import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;

import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImageclassEditCmd;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Component
public class ImageclassEditCmdExe {
    private final ImageclassGateway imageclassGateway;

    public AjaxResult execute(ImageclassEditCmd imageclassEditCmd){
        Imageclass imageclass = AppImageclassConvertor.INSTANCT.cmd2Imageclass(imageclassEditCmd);
        if (imageclassGateway.checkImageclassExistByName(imageclass)){
            throw new ServiceException("图片分类名称已存在");
        }
        if (imageclassGateway.updateImageclass(imageclass)){
            return AjaxResult.success();
        }
        return AjaxResult.error("修改图片分类不存在");
    }
}
