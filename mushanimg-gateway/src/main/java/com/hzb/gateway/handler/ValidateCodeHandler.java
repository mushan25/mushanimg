package com.hzb.gateway.handler;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.gateway.service.ValidateCodeService;
import org.apache.ibatis.cache.CacheException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {

    private final ValidateCodeService validateCodeService;

    public ValidateCodeHandler(ValidateCodeService validateCodeService) {
        this.validateCodeService = validateCodeService;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        AjaxResult ajax;
        try {
            ajax = validateCodeService.createCaptcha();
        }catch (CacheException e){
            return Mono.error(e);
        }
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
    }
}
