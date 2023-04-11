package com.hzb.gateway.config;

import com.alibaba.fastjson2.JSON;
import com.hzb.base.constant.TokenConstants;
import com.hzb.base.exception.RestErrorResponse;
import com.hzb.gateway.config.properties.IgnoreWhiteProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
@Component
@Slf4j
public class GatewayAuthFilter implements GlobalFilter, Ordered {

    private final IgnoreWhiteProperties ignoreWhite;
    private final TokenStore tokenStore;

    @Autowired
    public GatewayAuthFilter(IgnoreWhiteProperties ignoreWhite, TokenStore tokenStore) {
        this.ignoreWhite = ignoreWhite;
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1.获取请求url
        String requestUrl = exchange.getRequest().getPath().value();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 2.白名单放行
        for (String url : ignoreWhite.getWhites()) {
            if (pathMatcher.match(url, requestUrl)){
                return chain.filter(exchange);
            }
        }
        // 3.检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)){
            return buildReturnMono("没有认证", exchange);
        }
        // 4.判断是否为有效token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            boolean expired = oAuth2AccessToken.isExpired();
            if (expired){
                return buildReturnMono("认证令牌已过期", exchange);
            }
            return chain.filter(exchange);
        } catch (InvalidTokenException e){
            log.info("认证令牌无效：{}", token);
            return buildReturnMono("认证令牌无效", exchange);
        }
    }

    /**
     * 获取token
     * @return
     */
    private String getToken(ServerWebExchange exchange){
        String tokenStr = exchange.getRequest().getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        if (StringUtils.isBlank(tokenStr)){
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)){
            return null;
        }
        return token;
    }

    private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        String jsonString = JSON.toJSONString(new RestErrorResponse(error));
        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-type", "application/json;charset=UFT-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
