package com.hzb.file.executor.command;

import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
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
        if (imageclassGateway.addImageclass(AppImageclassConvertor.INSTANCT.CO2Imageclass(imageclassAddCmd.getImageclassCO()))){
            return AjaxResult.success();
        }
        throw new ServiceException("添加图片分类失败");
    }
}
