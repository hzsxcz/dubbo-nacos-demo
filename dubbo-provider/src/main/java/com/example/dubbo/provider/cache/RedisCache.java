package com.example.dubbo.provider.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Shiro Cache 的 Redis 实现，带 30 分钟自动过期。
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
    private static final long TTL_MINUTES = 30;

    private final RedisTemplate<String, Object> redis;
    private final String prefix;

    public RedisCache(RedisTemplate<String, Object> redis, String name) {
        this.redis = redis;
        this.prefix = "shiro:cache:" + name + ":";
    }

    private String key(K k) {
        return prefix + k.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(K k) throws CacheException {
        try {
            return (V) redis.opsForValue().get(key(k));
        } catch (Exception e) {
            log.warn("Redis get failed for key={}, fallback to DB", k, e);
            return null;
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        try {
            redis.opsForValue().set(key(k), v, TTL_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.warn("Redis put failed for key={}", k, e);
        }
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        try {
            redis.delete(key(k));
        } catch (Exception e) {
            log.warn("Redis remove failed for key={}", k, e);
        }
        return old;
    }

    @Override
    public void clear() throws CacheException {
        try {
            Set<String> keys = redis.keys(prefix + "*");
            if (keys != null && !keys.isEmpty()) {
                redis.delete(keys);
            }
        } catch (Exception e) {
            log.warn("Redis clear failed for prefix={}", prefix, e);
        }
    }

    @Override
    public int size() {
        try {
            Set<String> keys = redis.keys(prefix + "*");
            return keys != null ? keys.size() : 0;
        } catch (Exception e) {
            log.warn("Redis size failed", e);
            return 0;
        }
    }

    @Override
    public Set<K> keys() {
        try {
            return redis.keys(prefix + "*").stream()
                    .map(s -> (K) s.substring(prefix.length()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.warn("Redis keys failed", e);
            return java.util.Collections.emptySet();
        }
    }

    @Override
    public Collection<V> values() {
        return keys().stream().map(this::get).collect(Collectors.toList());
    }
}