package com.hzb.system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@SpringBootApplication(scanBasePackages = {"com.hzb.system","com.alibaba.cola","com.hzb.base.core","com.hzb.base.security"})
@EnableAspectJAutoProxy(exposeProxy = true)
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
