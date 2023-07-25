package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.dto.ImageclassRemoveCmd;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Component
public class ImageclassRemoveCmdExe {
    private final ImageclassGateway imageclassGateway;

    public AjaxResult execute(ImageclassRemoveCmd imageclassRemoveCmd){
        if (imageclassGateway.checkImageExist(imageclassRemoveCmd.getImageclassId())){
            return AjaxResult.error("图片分类下存在图片，不能删除");
        }
        if (imageclassGateway.deleteImageclass(imageclassRemoveCmd.getImageclassId(), imageclassRemoveCmd.getUserId())){
            return AjaxResult.success();
        }
        return AjaxResult.error("删除图片分类不存在");
    }
}
