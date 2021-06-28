package com.bootdo.common.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author rory.chen
 * @date 2021-01-24 14:18
 */
@Slf4j
public class RedisTemplateUtil {

    public static <T> T getRedisString(RedisTemplate redisTemplate, String key, Class<T> clazz){
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        Gson gson = new Gson();
        String value = gson.toJson(opsForValue.get(key));
        return gson.fromJson(value, clazz);
    }

    public static String getRedisString(RedisTemplate redisTemplate, String key){
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        Gson gson = new Gson();
        return gson.toJson(opsForValue.get(key));
    }

    public static <T> T getRedisString(String key, Class<T> clazz){
        RedisTemplate<String, String> redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String value = opsForValue.get(key);
        if (value == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(value, clazz);
    }

    public static String getRedisString(String key){
        RedisTemplate<String, String> redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(key);
    }

}
