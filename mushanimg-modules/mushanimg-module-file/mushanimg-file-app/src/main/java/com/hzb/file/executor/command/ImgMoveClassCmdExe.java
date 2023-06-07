package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.convertor.AppImgclassAconvertor;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.dto.ImgMoveClassCmd;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/7
 */
@Component
public class ImgMoveClassCmdExe {
    private final DomainService domainService;

    public ImgMoveClassCmdExe(DomainService domainService) {
        this.domainService = domainService;
    }

    public AjaxResult execute(ImgMoveClassCmd imgMoveClassCmd) {
        if (domainService.moveImageclass(AppImageConvertor.INSTANCT.cmd2Image(imgMoveClassCmd)
                , AppImgclassAconvertor.INSTANCT.cmd2Imageclass(imgMoveClassCmd))) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
