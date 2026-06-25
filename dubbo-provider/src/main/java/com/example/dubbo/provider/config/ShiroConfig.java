package com.example.dubbo.provider.config;

import com.example.dubbo.provider.cache.RedisCacheManager;
import com.example.dubbo.provider.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /** 自定义 Redis 缓存管理器（Redis 不可用时自动降级查库） */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        return new RedisCacheManager(factory);
    }

    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm realm,
                                                      RedisCacheManager cacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(cacheManager);
        return manager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        chain.addPathDefinition("/auth/login", "anon");
        chain.addPathDefinition("/auth/logout", "anon");
        chain.addPathDefinition("/**", "authc");
        return chain;
    }
}