package com.hzb.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@SpringBootApplication(scanBasePackages = {"com.hzb.system","com.alibaba.cola"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
