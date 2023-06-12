package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.domain.ability.DomainService;
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
    private final DomainService domainService;

    public AjaxResult execute(ImageclassRemoveCmd imageclassRemoveCmd){
        if (domainService.deleteImacgeclass(imageclassRemoveCmd.getImageclassIds(), imageclassRemoveCmd.getUserId())){
            return AjaxResult.success();
        }
        return AjaxResult.error("删除图片分类不存在");
    }
}
