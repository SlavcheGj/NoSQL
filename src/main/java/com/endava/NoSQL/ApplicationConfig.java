package com.endava.NoSQL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan(basePackages = {"com.endava.NoSQL"})
public class ApplicationConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer()); // za da ne se dodava cuden string na pocetok od kluc
        return template;
    }
    @Bean
    public ZSetOperations<String, Object> zSetOperations(){
        ZSetOperations<String,Object> zOperation = redisTemplate().opsForZSet();
        return zOperation;
    }

//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        return cacheManager;
//    }
}
