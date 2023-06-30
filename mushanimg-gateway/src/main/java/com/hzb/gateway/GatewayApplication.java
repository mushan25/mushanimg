package com.hzb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: hzb
 * @Date: 2023/4/10
 */
@SpringBootApplication(scanBasePackages = {"com.hzb.base.core","com.hzb.base.redis"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
