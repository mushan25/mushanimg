package com.hzb.gateway.config;

import com.hzb.gateway.handler.ValidateCodeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 路由配置信息
 *
 * @author: hzb
 * @Date: 2023/4/14
 */
@Configuration
public class RouterFunctionConfig {
    private final ValidateCodeHandler validateCodeHandler;

    public RouterFunctionConfig(ValidateCodeHandler validateCodeHandler) {
        this.validateCodeHandler = validateCodeHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route(
                RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                validateCodeHandler);
    }
}
