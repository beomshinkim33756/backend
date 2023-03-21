package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {

    KAKAO_BLOG_CACHE("kakaoBlogCacheStore", 30, 10000),
    RANK_CACHE("rankCacheStore", 1, 10)
    ;

    private final String cacheName;     // 캐시 이름
    private final int expireAfterWrite; // 만료시간
    private final int maximumSize;      // 최대 갯수
}
