package com.hzb.base.core.utils;

import com.alibaba.fastjson2.JSON;
import com.hzb.base.core.domain.ResultBody;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public class ServletUtils {

    /**
     * 设置webflux模型响应
     * @param response ServerHttpResponse
     * @param msg 响应内容
     * @return
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String msg) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResultBody<?> result = ResultBody.fail(HttpStatus.OK, msg.toString());
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
