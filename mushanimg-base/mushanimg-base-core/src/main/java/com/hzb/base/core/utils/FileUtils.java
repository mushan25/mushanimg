package com.hzb.base.core.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
public class FileUtils {

    public static File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            CheckUtils.isTrue(!file.mkdirs(), "创建文件夹失败", "创建文件夹失败");
        }
        return file;
    }

    public static void deleteTempFile(File tempFile){
        if (null != tempFile && !tempFile.delete()) {
            tempFile.deleteOnExit();
        }
    }

    public static File transferFile(MultipartFile file, String tempFilePath){
        try {
            File tempFile = File.createTempFile("minio", "temp", FileUtils.getFile(tempFilePath));
            file.transferTo(tempFile);
            return tempFile;
        } catch (Exception e) {
            throw new RuntimeException("图片上传失败");
        }
    }
}
