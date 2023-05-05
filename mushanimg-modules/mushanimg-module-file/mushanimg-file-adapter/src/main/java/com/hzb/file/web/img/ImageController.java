package com.hzb.file.web.img;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.api.ImageService;
import com.hzb.file.dto.ImgUploadCmd;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public AjaxResult upload(@RequestPart("img")MultipartFile img) throws Exception{
        ImgUploadCmd imgUploadCmd = new ImgUploadCmd();

        File tempFile = File.createTempFile("minio", "temp");
        img.transferTo(tempFile);
        String localFilePath = tempFile.getAbsolutePath();
        imgUploadCmd.setImgName(img.getOriginalFilename())
                .setSize(img.getSize())
                .setLocalFilePath(localFilePath);
        return imageService.uploadImg(imgUploadCmd);
    }
}
