package com.hzb.file.executor.command;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.convertor.AppImageclassConvertor;
import com.hzb.file.domain.ability.DomainService;
import com.hzb.file.dto.ImgMoveClassCmd;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/7
 */
@AllArgsConstructor
@Component
public class ImgMoveClassCmdExe {
    private final DomainService domainService;

    public AjaxResult execute(ImgMoveClassCmd imgMoveClassCmd) {
        if (domainService.moveImageclass(AppImageConvertor.INSTANCT.cmd2Image(imgMoveClassCmd)
                , AppImageclassConvertor.INSTANCT.cmd2Imageclass(imgMoveClassCmd))) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
