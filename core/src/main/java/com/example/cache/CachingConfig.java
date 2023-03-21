package com.example.cache;

import com.example.enums.CacheType;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
@Slf4j
public class CachingConfig {

    @Bean
    public List<CaffeineCache> caffeineCaches() { // 캐시 설정
        return  Arrays.stream(CacheType.values())
                .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder()
                        .recordStats()
                        .expireAfterWrite(cache.getExpireAfterWrite(),TimeUnit.SECONDS)
                        .maximumSize(cache.getMaximumSize())
                        .build()
                ))
                .collect(Collectors.toList());
    }

    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) { // 캐시 설정
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
}
