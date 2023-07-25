package com.hzb.file.executor.command;

import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.dto.ImageclassAddCmd;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Component
public class ImageclassAddCmdExe {
    private final ImageclassGateway imageclassGateway;

    public AjaxResult execute(ImageclassAddCmd imageclassAddCmd){
        if (imageclassGateway.countImageclass(imageclassAddCmd.getUserId()) > Constants.IMAGE_CLASS_MAX_COUNT){
            throw new ServiceException("图片分类数量超过最大限制");
        }
        Imageclass imageclass = AppImageclassConvertor.INSTANCT.cmd2Imageclass(imageclassAddCmd);

        if (imageclassGateway.checkImageclassExistByName(imageclass)){
            throw new ServiceException("图片分类名称已存在");
        }
        if (imageclassGateway.addImageclass(imageclass)){
            return AjaxResult.success();
        }
        throw new ServiceException("添加图片分类失败");
    }
}
