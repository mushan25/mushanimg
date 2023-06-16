package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;

import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
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
        if (imageclassGateway.updateImageclass(AppImageclassConvertor.INSTANCT.CO2Imageclass(imageclassEditCmd.getImageclassCO()))){
            return AjaxResult.success();
        }
        return AjaxResult.error("修改图片分类不存在");
    }
}
