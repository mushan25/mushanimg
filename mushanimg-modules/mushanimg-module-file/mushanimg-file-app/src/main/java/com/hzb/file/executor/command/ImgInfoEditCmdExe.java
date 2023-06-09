package com.hzb.file.executor.command;

import com.alibaba.cola.exception.SysException;
import com.hzb.base.core.exception.ServiceException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.convertor.AppImageConvertor;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.dto.ImgInfoEditCmd;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/6/5
 */
@AllArgsConstructor
@Component
public class ImgInfoEditCmdExe {
    private final ImageGateway imageGateway;

    public AjaxResult execute(ImgInfoEditCmd imgInfoEditCmd){
        if (imageGateway.updateImgInfo(AppImageConvertor.INSTANCT.cmd2Image(imgInfoEditCmd))){
            return AjaxResult.success();
        }
        throw new ServiceException("修改图片信息失败");
    }
}
