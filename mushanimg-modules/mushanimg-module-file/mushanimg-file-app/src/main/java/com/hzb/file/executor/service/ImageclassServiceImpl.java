package com.hzb.file.executor.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageclassService;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageclassCO;
import com.hzb.file.dto.clientobject.ImageclassListCO;
import com.hzb.file.executor.command.ImageclassAddCmdExe;
import com.hzb.file.executor.command.ImageclassEditCmdExe;
import com.hzb.file.executor.command.ImageclassRemoveCmdExe;
import com.hzb.file.executor.command.query.ImageclassInfoQryExe;
import com.hzb.file.executor.command.query.ImageclassListQryExe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@Service
public class ImageclassServiceImpl implements ImageclassService {
    private final ImageclassAddCmdExe imageclassAddCmdExe;
    private final ImageclassEditCmdExe imageclassEditCmdExe;
    private final ImageclassRemoveCmdExe imageclassRemoveCmdExe;
    private final ImageclassListQryExe imageclassListQryExe;
    private final ImageclassInfoQryExe imageclassInfoQryExe;

    @Override
    public AjaxResult addImageclss(ImageclassAddCmd imageclassAddCmd) {
        return imageclassAddCmdExe.execute(imageclassAddCmd);
    }

    @Override
    public AjaxResult editImageclss(ImageclassEditCmd imageclassEditCmd) {
        return imageclassEditCmdExe.execute(imageclassEditCmd);
    }

    @Override
    public AjaxResult removeImageclss(ImageclassRemoveCmd imageclassRemoveCmd) {
        return imageclassRemoveCmdExe.execute(imageclassRemoveCmd);
    }

    @Override
    public PageResponse<ImageclassListCO> getImageclassList(ImageclassListQry imageclassListQry) {
        return imageclassListQryExe.execute(imageclassListQry);
    }

    @Override
    public SingleResponse<ImageclassCO> getImageclassInfo(ImageclassInfoQry imageclassInfoQry) {
        return imageclassInfoQryExe.execute(imageclassInfoQry);
    }
}
