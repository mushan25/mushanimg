package com.hzb.base.redis.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Redis使用FastJson2序列化
 *
 * @author: hzb
 * @Date: 2023/4/12
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    static final Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter(
            // 按需加上需要支持自动类型的类名前缀，范围越小越安全
            "org.springframework.security.core.authority.SimpleGrantedAuthority",
            "com.hzb.base.security.form.LoginUser",
            "com.hzb.base.security.form.UserInfo",
            "com.hzb.base.security.form.Password"
    );

    private final Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {

        if (t == null){
            return new byte[0];
        }
        return JSON.toJSONBytes(t, JSONWriter.Feature.WriteClassName);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0){
            return null;
        }
        return JSON.parseObject(bytes, clazz, AUTO_TYPE_FILTER);
    }
}
