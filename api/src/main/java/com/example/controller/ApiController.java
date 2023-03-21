package com.example.controller;

import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.http.response.FindBlogResponseDto;
import com.example.model.http.response.FindRankResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/api/v1/find/blog")
    public ResponseEntity findBlog(
            @Validated FindBlogRequestDto requestDto
    ) throws Exception { // 블로그 검색 API
        BlogResponseDto blogResponseDto = apiService.findBlogList(new BlogRequestDto(requestDto.checkForgery())); // 블로그 리스트 조회
        try {
            apiService.incrementCount(requestDto.getKeyword()); // 키워드 조회 값 증가
        } catch (DataIntegrityViolationException e) {
            log.error("INSERT 안된 키워드 동시 INSERT 유니크 오류, 키워드 : {}", requestDto.getKeyword());
        } catch (Exception e) {
            log.error("{}", e);
        }
        FindBlogResponseDto body = new FindBlogResponseDto(blogResponseDto); // 응답값
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank() { // 인기 검색어 조회 API
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(); // 키워드 랭크 조회 및 업데이트
        FindRankResponseDto body = new FindRankResponseDto(keywordResponseDto.getRanks()); // 응답값
        return ResponseEntity.ok().body(body);
    }
}
