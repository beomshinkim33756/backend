package com.example.controller;

import com.example.enums.CacheType;
import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.http.request.FindUrlBlogRequestDto;
import com.example.model.http.response.FindBlogResponseDto;
import com.example.model.http.response.FindRankResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;
    private final CacheManager cacheManager;

    @GetMapping("/api/v1/find/blog")
    public ResponseEntity findBlog(
            @Validated FindBlogRequestDto requestDto
    ) throws Exception { // 블로그 검색 API
        BlogResponseDto blogResponseDto = apiService.findBlogList(new BlogRequestDto(requestDto.checkForgery())); // 블로그 리스트 조회
        apiService.incrementCount(requestDto.getKeyword()); // 키워드 조회 값 증가
        FindBlogResponseDto body = new FindBlogResponseDto(blogResponseDto); // 응답값
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank() { // 인기 검색어 조회 API
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(); // 키워드 랭크 조회 및 업데이트
        if (keywordResponseDto == null && cacheManager.getCache(CacheType.RANK_CACHE.getCacheName()) != null) cacheManager.getCache(CacheType.RANK_CACHE.getCacheName()).clear(); // 캐시 삭제
        FindRankResponseDto body = new FindRankResponseDto(keywordResponseDto); // 응답값
        return ResponseEntity.ok().body(body);
    }


    @GetMapping("/api/v1/find/url/blog")
    public ResponseEntity findUrlBlog(
            @Validated FindUrlBlogRequestDto requestDto
    ) throws Exception { // 블로그 URL 검색 API
        BlogResponseDto blogResponseDto = apiService.findUrlBlogList(new BlogRequestDto(requestDto.checkForgery())); // 블로그 리스트 조회
        apiService.incrementCount(requestDto.getKeyword()); // 키워드 조회 값 증가
        FindBlogResponseDto body = new FindBlogResponseDto(blogResponseDto); // 응답값
        return ResponseEntity.ok().body(body);
    }
}
