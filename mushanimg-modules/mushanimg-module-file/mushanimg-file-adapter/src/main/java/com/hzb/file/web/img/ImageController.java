package com.hzb.file.web.img;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.hzb.base.core.annotation.Log;
import com.hzb.base.core.annotation.StartPage;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.*;
import com.hzb.file.dto.clientobject.ImageCO;
import com.hzb.file.dto.clientobject.ImageListCO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@AllArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;

    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Log("图片上传")
    public AjaxResult upload(@RequestPart("img")MultipartFile img) {
        return imageService.uploadImg(img);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:gallery:list')")
    @StartPage
    @Log("图片列表")
    public PageResponse<ImageListCO> list(@RequestBody ImgListQry imgListQry){
        Long userId = SecurityUtils.getUserId();
        imgListQry.setUserId(userId);
        return imageService.getImageList(imgListQry);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:gallery:list')")
    @Log("图片信息")
    public SingleResponse<ImageCO> getImgInfo(@PathVariable("id") Long id){
        ImgInfoQry imgInfoQry = new ImgInfoQry(id, SecurityUtils.getUserId());
        return imageService.getImageInfo(imgInfoQry);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('sys:gallery:list')")
    @Log("编辑图片信息")
    public AjaxResult editImageInfo(@RequestBody @Validated ImgInfoEditCmd imgInfoEditCmd){
        imgInfoEditCmd.setUserId(SecurityUtils.getUserId());
        return imageService.editImageInfo(imgInfoEditCmd);
    }

    @DeleteMapping("/remove")
    @Log("删除图片")
    public AjaxResult removeImage(@RequestBody @Validated ImgRemoveCmd imgRemoveCmd){
        return imageService.removeImage(imgRemoveCmd);
    }

    @PutMapping("/move")
    @PreAuthorize("hasAnyAuthority('sys:gallery:list')")
    @Log("移动图片到其他分类")
    public AjaxResult moveImage2OtherClass(@RequestBody @Validated ImgMoveClassCmd imgMoveClassCmd){
        imgMoveClassCmd.setUserId(SecurityUtils.getUserId());
        return imageService.moveImage2OtherClass(imgMoveClassCmd);
    }
}
