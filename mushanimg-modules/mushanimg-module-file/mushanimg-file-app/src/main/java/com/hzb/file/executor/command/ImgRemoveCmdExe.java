package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.dto.ImgRemoveCmd;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/7
 */
@Component
public class ImgRemoveCmdExe {
    private final DomainService domainService;

    public ImgRemoveCmdExe(DomainService domainService) {
        this.domainService = domainService;
    }

    public AjaxResult execute(ImgRemoveCmd imgRemoveCmd){
        if (domainService.deleteImg(imgRemoveCmd.getImgIds(), imgRemoveCmd.getUserId())){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
