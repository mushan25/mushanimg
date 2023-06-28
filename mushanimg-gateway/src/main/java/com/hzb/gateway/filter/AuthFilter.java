package com.hzb.gateway.filter;

import com.hzb.base.core.constant.CacheConstants;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.constant.SecurityConstants;
import com.hzb.base.core.constant.TokenConstants;
import com.hzb.base.core.utils.IpUtils;
import com.hzb.base.core.utils.JwtUtils;
import com.hzb.base.core.utils.ServletUtils;
import com.hzb.base.redis.service.RedisService;
import com.hzb.gateway.config.properties.IgnoreWhiteProperties;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 网关鉴权
 *
 * @author: hzb
 * @Date: 2023/4/12
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private final IgnoreWhiteProperties ignoreWhite;

    private final RedisService redisService;

    public AuthFilter(IgnoreWhiteProperties ignoreWhite, RedisService redisService) {
        this.ignoreWhite = ignoreWhite;
        this.redisService = redisService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String ipAddr = IpUtils.getIpAddr(request);
        if (redisService.hasKey(CacheConstants.BLACK_IP_KEY + ipAddr)) {
            return unauthorizedResponse(exchange, "黑名单IP");
        }
        Integer requestCount = redisService.getCacheObject(CacheConstants.REQUEST_COUNT_MINUTE + ipAddr);
        if (null == requestCount){
            requestCount = 0;
        }
        if ( requestCount > CacheConstants.MAX_REQUEST_COUNT){
            redisService.setCacheObject(CacheConstants.BLACK_IP_KEY + ipAddr, ipAddr, 1L, TimeUnit.DAYS);
            log.info("========= IP:{}已被加入黑名单 =========", ipAddr);
            return unauthorizedResponse(exchange, "请求过于频繁");
        }
        redisService.setCacheObject(CacheConstants.REQUEST_COUNT_MINUTE + ipAddr, requestCount + 1, 1L, TimeUnit.MINUTES);

        String url = request.getURI().getPath();
        AntPathMatcher matcher = new AntPathMatcher();
        List<String> whites = ignoreWhite.getWhites();
        // 跳过不需要验证的路径
        for (String white : whites) {
            if (matcher.match(url, white)){
                return chain.filter(exchange);
            }
        }

        String token = getToken(request);
        if (StringUtils.isEmpty(token)){
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null){
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确");
        }

        String userKey = JwtUtils.getUserKey(token);

        Boolean isLogin = redisService.hasKey(getTokenKey(userKey));
        if (!isLogin){
            return unauthorizedResponse(exchange, "登录状态已过期");
        }

        String userId = JwtUtils.getUserId(claims);
        String userName = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)){
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置用户登录信息到请求
        addHeader(mutate, SecurityConstants.USER_KEY, userKey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userId);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, userName);
        // 内部请求来源参数清除
        removeHeader(mutate);

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value){
        if (value == null){
            return ;
        }
        String valueStr = value.toString();
        String valueEncode;
        valueEncode = URLEncoder.encode(valueStr, StandardCharsets.UTF_8);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate){
        mutate.headers(httpHeaders ->
            httpHeaders.remove(SecurityConstants.FROM_SOURCE)
        ).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg){
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        ServerHttpResponse response = exchange.getResponse();
        return ServletUtils.webFluxResponseWriter(response, msg);
    }

    private String getTokenKey(String token){
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    private String getToken(ServerHttpRequest request){
        return request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
