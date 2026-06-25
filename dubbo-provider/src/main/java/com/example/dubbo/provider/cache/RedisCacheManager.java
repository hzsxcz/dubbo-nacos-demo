package com.example.dubbo.provider.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Shiro CacheManager，Redis 优先，不可用时降级为 null（触发 Realm 查库）。
 */
public class RedisCacheManager implements CacheManager {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheManager.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final ConcurrentMap<String, Cache<?, ?>> caches = new ConcurrentHashMap<>();
    private volatile boolean redisAvailable = true;

    public RedisCacheManager(RedisConnectionFactory factory) {
        this.redisTemplate = new RedisTemplate<>();
        this.redisTemplate.setConnectionFactory(factory);
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        this.redisTemplate.afterPropertiesSet();
        checkRedis();
    }

    private void checkRedis() {
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            redisAvailable = true;
            log.info("Redis connected - Shiro cache enabled");
        } catch (Exception e) {
            redisAvailable = false;
            log.warn("Redis unavailable, Shiro will fallback to DB query: {}", e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (!redisAvailable) {
            return null; // 降级：返回 null 让 Shiro 直接走 Realm 查库
        }
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
        } catch (Exception e) {
            redisAvailable = false;
            log.warn("Redis connection lost, fallback to DB");
            return null;
        }
        return (Cache<K, V>) caches.computeIfAbsent(name,
                n -> new RedisCache<>(redisTemplate, n));
    }
}