package com.hzb.file.web.img;

import com.alibaba.cola.dto.PageResponse;
import com.hzb.base.core.annotation.StartPage;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.security.utils.SecurityUtils;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.ImgListQry;
import com.hzb.file.dto.ImgUploadCmd;
import com.hzb.file.dto.clientobject.ImageCO;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
@RestController
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult upload(@RequestPart("img")MultipartFile img) throws Exception{
        ImgUploadCmd imgUploadCmd = new ImgUploadCmd();
        Long userId = SecurityUtils.getUserId();

        File tempFile = File.createTempFile("minio", "temp");
        img.transferTo(tempFile);
        String localFilePath = tempFile.getAbsolutePath();
        imgUploadCmd.setImgName(img.getOriginalFilename())
                .setSize(img.getSize())
                .setLocalFilePath(localFilePath)
                .setUserId(userId);
        return imageService.uploadImg(imgUploadCmd);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin')")
    @StartPage
    public PageResponse<ImageCO> list(@RequestBody ImgListQry imgListQry){
        Long userId = SecurityUtils.getUserId();
        imgListQry.setUserId(userId);
        return imageService.getImageList(imgListQry);
    }
}
