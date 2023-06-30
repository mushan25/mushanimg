package com.hzb.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hzb
 * @Date: 2023/4/28
 */
@SpringBootApplication(scanBasePackages = {"com.hzb.file", "com.alibaba.cola","com.hzb.base.core",
        "com.hzb.base.security", "com.hzb.base.redis"})
public class FileApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(FileApplication.class, args);
        }catch (Throwable e){
            e.printStackTrace();
        }

    }
}
