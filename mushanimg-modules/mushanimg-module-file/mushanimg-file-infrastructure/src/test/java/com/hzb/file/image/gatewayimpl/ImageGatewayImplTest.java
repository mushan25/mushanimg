package com.hzb.file.image.gatewayimpl;

import com.alibaba.cola.exception.SysException;
import com.hzb.file.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import io.minio.messages.VersioningConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: hzb
 * @Date: 2023/6/5
 */
@Slf4j
class ImageGatewayImplTest {
    @Test
    void deleteImgMinio() {
        ArrayList<String> objectNameList = new ArrayList<>();
        objectNameList.add("dev/background.jpg");
        objectNameList.add("456");
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://123.249.39.173:9000")
                .credentials("mushan0205", "Mushan0205@q")
                .build();
        try {
            List<DeleteObject> objects = objectNameList.stream().map(DeleteObject::new).toList();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs
                    .builder().bucket("img").objects(objects).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                objectNameList.remove(error.objectName());
                log.info("Error in deleting object " + error.objectName() + "; " + error.message());
            }
            log.info("objectNameList:{}", objectNameList);

        } catch (Exception e) {
            log.error("删除minio文件出错,objectName:{}, message{}:", objectNameList, e.getMessage());
            throw new SysException(e.getMessage());
        }
    }
}