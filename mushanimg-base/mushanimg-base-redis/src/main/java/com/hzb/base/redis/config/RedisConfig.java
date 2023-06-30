package com.hzb.base.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis配置
 *
 * @author: hzb
 * @Date: 2023/4/12
 */
@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings({"unchecked", "rawtypes"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        FastJson2JsonRedisSerializer serialize = new FastJson2JsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(serialize);
        template.setValueSerializer(serialize);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(serialize);
        template.setHashValueSerializer(serialize);

        template.afterPropertiesSet();
        return template;
    }
}
