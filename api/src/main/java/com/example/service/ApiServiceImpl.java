package com.example.service;

import com.example.entities.KeywordTb;
import com.example.enums.CacheType;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.naver.NaverBlogApiClientRequestDto;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.naver.NaverBlogApiClient;
import com.example.prop.KakaoBlogProp;
import com.example.kakao.KakaoBlogApiClient;
import com.example.prop.NaverBlogProp;
import com.example.repositories.KeywordTbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final KakaoBlogApiClient kakaoBlogApiClient;
    private final NaverBlogApiClient naverBlogApiClient;
    private final KakaoBlogProp kakaoBlogProp;
    private final NaverBlogProp naverBlogProp;
    private final KeywordTbRepository keywordTbRepository;
    private final CacheManager cacheManager;

    /**
     * 카카오 API조회 실패시 네이버 API 조회
     *
     * 카카오 API 이외 검색 소스 추가시 해당 소스 external-api 추가후
     * 교체 혹은 언터페이스 교체
     *
     * @param blogRequestDto
     * @return
     */
    @Override
    public BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) {
        KakaoBlogApiClientRequestDto kakaoBlogApiClientRequestDto = new KakaoBlogApiClientRequestDto(blogRequestDto, kakaoBlogProp);
        BlogResponseDto blogResponseDto = kakaoBlogApiClient.findBlog(kakaoBlogApiClientRequestDto); // 카카오 블로그 호출
        if (blogResponseDto == null) { // 카카오톡 블로그 불러오기 실패시
            log.debug("[네이버 블로그 조회] =======>");
            cacheManager.getCache(CacheType.KAKAO_BLOG_CACHE.getCacheName()).evict(kakaoBlogApiClientRequestDto.getCacheKey()); // 캐시 삭제
            blogResponseDto = naverBlogApiClient.findBlog(new NaverBlogApiClientRequestDto(blogRequestDto, naverBlogProp)); // 네이버 블로그 호출
        }
        return blogResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "rankCacheStore") // 캐시 만료 1초 설정
    public KeywordResponseDto findKeywordRank() {
        try {
            log.debug("인기 검색어 조회 ===>");
            AtomicInteger order = new AtomicInteger(0);
            List<KeywordRankDto> ranks = keywordTbRepository.findTop10ByOrderByCountDesc().stream().map(it -> new KeywordRankDto(it, order.incrementAndGet())).collect(Collectors.toList());
            if (ranks == null || ranks.size() == 0) {
                return null;
            }
            return new KeywordResponseDto(ranks);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Async
    @Transactional
    public void incrementCount(String keyword) { // 카운팅 증가
        if (StringUtils.isBlank(keyword)) return;
        KeywordTb keywordTb = keywordTbRepository.findByKeyword(keyword); // LOCK 설정
        if (keywordTb != null) {
            keywordTb.setCount(keywordTb.getCount() + 1);
        } else {
            keywordTb = new KeywordTb();
            keywordTb.setKeyword(keyword);
            keywordTb.setCount(1L);
        }
        keywordTbRepository.save(keywordTb);
    }




}
