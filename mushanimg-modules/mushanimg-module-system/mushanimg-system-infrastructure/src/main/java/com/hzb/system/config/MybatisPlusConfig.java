package com.hzb.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
@Configuration
@MapperScan("com.hzb.system.*.gatewayimpl.database")
public class MybatisPlusConfig {
}
