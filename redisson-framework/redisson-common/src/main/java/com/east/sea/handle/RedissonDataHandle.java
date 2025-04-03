package com.east.sea.handle;

import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * redisson操作
 * @author : tztang
 * @since 2025/04/03
 **/
@AllArgsConstructor
public class RedissonDataHandle {
    
    private final RedissonClient redissonClient;
    
    public String get(String key){
        return (String) redissonClient.getBucket(key).get();
    }
    
    public void set(String key, String value){
        redissonClient.getBucket(key).set(value);
    }
    
    public void set(String key, String value, Duration duration){
        redissonClient.getBucket(key).set(value, duration);
    }

}
