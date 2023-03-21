package com.example.cache;

import com.example.model.keyword.dto.KeywordRankDto;
import com.example.repositories.KeywordTbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final KeywordTbRepository keywordTbRepository;

    @Cacheable(cacheNames = "rankCacheStore") // 캐시 만료 1분 설정
    public List<KeywordRankDto> findRanks() {
        try {
            log.debug("[랭킹 조회] ============> ");
            AtomicInteger order = new AtomicInteger(0);
            return keywordTbRepository.findTop10ByOrderByCountDesc().stream().map(it -> new KeywordRankDto(it, order.incrementAndGet())).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
}
