package com.east.sea.redis.config;

import com.east.sea.redis.RedisCacheImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis封装实现配置
 * @author : tztang
 * @since 2025/04/03
 **/
@ConditionalOnProperty("spring.redis.host")
public class RedisCacheAutoConfig {
    
    @Bean
    public RedisCacheImpl redisCache(@Qualifier("redisToolStringRedisTemplate") StringRedisTemplate stringRedisTemplate){
        return new RedisCacheImpl(stringRedisTemplate);
    }

}
