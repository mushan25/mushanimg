package com.hzb.gateway.handler;

import com.hzb.base.core.exception.CaptchaException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.gateway.service.ValidateCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
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
@AllArgsConstructor
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {

    private final ValidateCodeService validateCodeService;

    @Override
    @NonNull
    public Mono<ServerResponse> handle(@NonNull ServerRequest request) {
        AjaxResult ajax;
        try {
            ajax = validateCodeService.createCaptcha();
        }catch (CaptchaException e){
            return Mono.error(e);
        }
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
    }
}
