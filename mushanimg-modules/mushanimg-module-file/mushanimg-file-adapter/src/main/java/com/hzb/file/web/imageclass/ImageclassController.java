package com.hzb.file.web.imageclass;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.annotation.Log;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.ImageclassService;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageclassCO;
import com.hzb.file.dto.clientobject.ImageclassListCO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@AllArgsConstructor
@RestController
public class ImageclassController {
    private final ImageclassService imageclassService;

    @PutMapping("/addImageclass")
    @Log("添加图片分类")
    public AjaxResult addImageclass(@RequestBody @Validated ImageclassAddCmd imageclassAddCmd){
        imageclassAddCmd.getImageclassCO().setUserId(SecurityUtils.getUserId());
        return imageclassService.addImageclss(imageclassAddCmd);
    }

    @PutMapping("/editImageclass")
    @Log("修改图片分类")
    public AjaxResult editImageclass(@RequestBody @Validated ImageclassEditCmd imageclassEditCmd){
        imageclassEditCmd.getImageclassCO().setUserId(SecurityUtils.getUserId());
        return imageclassService.editImageclss(imageclassEditCmd);
    }

    @DeleteMapping("/removeImageclass")
    @Log("删除图片分类")
    public AjaxResult removeImageclass(@RequestBody @Validated ImageclassRemoveCmd imageclassRemoveCmd){
        imageclassRemoveCmd.setUserId(SecurityUtils.getUserId());
        return imageclassService.removeImageclss(imageclassRemoveCmd);
    }

    @GetMapping("/getImageclassList")
    @Log("获取图片分类列表")
    public PageResponse<ImageclassListCO> getImageclassList(){
        ImageclassListQry imageclassListQry = new ImageclassListQry();
        imageclassListQry.setUserId(SecurityUtils.getUserId());
        return imageclassService.getImageclassList(imageclassListQry);
    }

    @PostMapping("/getImageclassInfo")
    @Log("获取图片分类信息")
    public SingleResponse<ImageclassCO> getImageclassInfo(@RequestBody @Validated ImageclassInfoQry imageclassInfoQry){
        imageclassInfoQry.setUserId(SecurityUtils.getUserId());
        return imageclassService.getImageclassInfo(imageclassInfoQry);
    }
}
